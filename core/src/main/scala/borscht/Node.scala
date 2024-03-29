package borscht

import borscht.parsers.given

import scala.annotation.{tailrec, targetName}

enum NodeType:
  case seq
  case cfg
  case scalar

sealed trait Node:
  def meta: Meta

  infix def withMeta(meta: Meta): Node

  def position: Position

  def `type`: NodeType

  def toString: String

  final def as[T](using parser: NodeParser[T]): T = try parser(this)
  catch {
    case e: Exception if !e.isInstanceOf[CfgException] => throw NodeParserException(position, e)
  }

trait ScalarNode extends Node:
  def value: Any

  def asString: String = value.toString

  override def withMeta(meta: Meta): ScalarNode

  final def `type`: NodeType = NodeType.scalar

  override def toString: String = s"${getClass.getName}($value)"

trait SeqNode extends Node with Iterable[Node]:
  override def withMeta(meta: Meta): SeqNode

  final def `type`: NodeType = NodeType.seq

  final def list[T](using parser: NodeParser[T]): List[T] = (iterator map parser).toList

  final def option[T](using parser: NodeParser[T]): Option[T] = size match
    case 0 => None
    case 1 => Some(parser(head))
    case n => throw NodeParserException(s"Node should be empty or contain a single element, but $n found", position)

  override def toString: String = mkString(s"${getClass.getName}([", ", ", "])")

trait CfgNode extends Node with Iterable[(String, Node)]:
  def child(key: String): Option[Node]

  override infix def withMeta(meta: Meta): CfgNode

  @targetName("merge")
  def ++(that: CfgNode): CfgNode = CfgNode.Merged(this, that)

  final def apply[T: NodeParser](ref: String*): T =
    get[T](ref*) getOrElse {
      throw NodeNotFoundException(ref, position)
    }

  final def get[T: NodeParser](ref: String*): Option[T] = node(ref*) map (_.as[T])

  final def list[T: NodeParser](ref: String*): List[T] = get[List[T]](ref*) getOrElse Nil

  final def set[T: NodeParser](ref: String*): Set[T] = get[Set[T]](ref*) getOrElse Set.empty

  final def map[T: NodeParser](ref: String*): Map[String, T] = get[Map[String, T]](ref*) getOrElse Map.empty

  final def properties(ref: String*): Map[String, String] =
    if ref.isEmpty then (this foldLeft Map.empty) { case (r, (k, n)) => properties(k, n, r) }
    else node(ref*) map (properties(ref.mkString("."), _, Map.empty)) getOrElse Map.empty

  private def properties(prefix: String, node: Node, result: Map[String, String]): Map[String, String] = node match
    case scalar: ScalarNode => result + (prefix -> scalar.asString)
    case cfg: CfgNode =>
      val p = prefix + "."
      (cfg foldLeft result) { case (r, (k, n)) => properties(p + k, n, r) }
    case seq: SeqNode =>
      val p = prefix + "."
      (seq.iterator.zipWithIndex foldLeft result) { case (r, (n, i)) => properties(p + i, n, r) }

  final def node(ref: String*): Option[Node] = if ref.isEmpty then Some(this)
  else
    val it = ref.iterator

    @tailrec
    def loop(cfg: CfgNode): Option[Node] =
      val next = cfg.child(it.next)
      if it.hasNext then
        next match
          case Some(nextCfg: CfgNode) => loop(nextCfg)
          case _                      => None
      else next

    loop(this)

  final def optionalOneOf[T](map: Map[String, NodeParser[? <: T]]): Option[T] =
    val mappings = map flatMap { (key, parser) =>
      child(key) map { node =>
        key -> (() => parser(node))
      }
    }
    if mappings.size > 1 then
      throw NodeParserException(
        s"No more than one key of ${map.keys.mkString(", ")} should exist, but ${mappings.keys.mkString(", ")} found",
        position
      )
    else mappings.headOption map (_._2())

  final def oneOf[T](map: Map[String, NodeParser[? <: T]]): T = optionalOneOf(map) getOrElse {
    throw NodeParserException(s"No one of ${map.keys.mkString(", ")} was found", position)
  }

  final def contains(key: String): Boolean = child(key).isDefined

  final def `type`: NodeType = NodeType.cfg

  override def toString: String = iterator.mkString(s"${getClass.getName}(", ", ", ")")

object CfgNode:
  private def merge(optFallback: Option[Node], optMain: Option[Node]): Option[Node] = (optFallback, optMain) match
    case (Some(fallback: CfgNode), Some(main: CfgNode)) => Some(Merged(fallback, main))
    case (_, None)                                      => optFallback
    case _                                              => optMain

  private case class Merged(fallback: CfgNode, main: CfgNode) extends CfgNode:
    val meta: Meta = fallback.meta ++ main.meta

    override def child(key: String): Option[Node] = merge(fallback.child(key), main.child(key))

    override def iterator: Iterator[(String, Node)] =
      def updated = (fallback.iterator map { (key, node) =>
        key -> merge(Some(node), main.child(key)).get
      }).toMap

      updated.iterator ++ (main.iterator filterNot { (key, _) => updated.contains(key) })

    override def withMeta(meta: Meta): Merged = Merged(fallback withMeta meta, main withMeta meta)

    override def position: Position = fallback.position + main.position

  case class Empty(meta: Meta) extends CfgNode:
    override def child(key: String): Option[Node] = None

    override def iterator: Iterator[(String, Node)] = Iterator.empty

    override def withMeta(meta: Meta): Empty = new Empty(meta)

    override def position: Position = Position.None

  object Empty extends Empty(Meta.Empty)
