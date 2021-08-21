package borscht

import borscht.parsers.given

import scala.annotation.{tailrec, targetName}

enum NodeType:
  case seq
  case cfg
  case scalar

sealed trait Node:
  def meta: Meta

  def withMeta(meta: Meta): Node

  def position: Position

  def `type`: NodeType

  def toString: String

  final def parse[T](using parser: NodeParser[T]) = parser(this)

trait ScalarNode extends Node:
  def value: Any

  def asString: String = value.toString

  override def withMeta(meta: Meta): ScalarNode

  final def `type`: NodeType = NodeType.scalar

  override def toString: String = s"${getClass.getName}($value)"

trait SeqNode extends Node with Iterable[Node] :
  override def withMeta(meta: Meta): SeqNode

  final def `type`: NodeType = NodeType.seq

  override def toString: String = mkString(s"${getClass.getName}([", ", ", "])")

trait CfgNode extends Node with Iterable[(String, Node)] :
  def child(key: String): Option[Node]

  override def withMeta(meta: Meta): CfgNode

  @targetName("merge")
  def ++(that: CfgNode): CfgNode = new CfgNode.Merged(this, that)

  final def apply[T: NodeParser](ref: String*): T =
    get[T](ref: _*) getOrElse {
      throw NodeNotFoundException(ref, position)
    }

  final def get[T: NodeParser](ref: String*): Option[T] = node(ref: _*) map { n =>
    try n.parse[T] catch {
      case e: Exception if !e.isInstanceOf[CfgException] => throw NodeParserException(n.position, e)
    }
  }

  final def list[T: NodeParser](ref: String*): List[T] = get[List[T]](ref: _*) getOrElse Nil

  final def set[T: NodeParser](ref: String*): Set[T] = get[Set[T]](ref: _*) getOrElse Set.empty

  final def map[T: NodeParser](ref: String*): Map[String, T] = get[Map[String, T]](ref: _*) getOrElse Map.empty

  final def node(ref: String*): Option[Node] = if (ref.isEmpty) Some(this) else
    val it = ref.iterator

    @tailrec
    def loop(cfg: CfgNode): Option[Node] =
      val next = cfg.child(it.next)
      if (it.hasNext)
        next match
          case Some(nextCfg: CfgNode) => loop(nextCfg)
          case _ => None
      else next

    loop(this)

  final def contains(key: String): Boolean = child(key).isDefined

  final def `type`: NodeType = NodeType.cfg

  override def toString = iterator.mkString(s"${getClass.getName}(", ", ", ")")

object CfgNode:
  private def merge(optFallback: Option[Node], optMain: Option[Node]): Option[Node] = (optFallback, optMain) match
    case (Some(fallback: CfgNode), Some(main: CfgNode)) => Some(new Merged(fallback, main))
    case (_, None) => optFallback
    case _ => optMain

  private case class Merged(fallback: CfgNode, main: CfgNode) extends CfgNode :
    val meta = fallback.meta ++ main.meta

    override def child(key: String): Option[Node] = merge(fallback.child(key), main.child(key))

    override def iterator: Iterator[(String, Node)] =
      def updated = (fallback.iterator map { (key, node) =>
        key -> merge(Some(node), main.child(key)).get
      }).toMap

      updated.iterator ++ (main.iterator filterNot { (key, _) => updated.contains(key) })

    override def withMeta(meta: Meta): Merged = Merged(fallback withMeta meta, main withMeta meta)

    override def position: Position = fallback.position + main.position

  case class Empty(val meta: Meta) extends CfgNode :
    override def child(key: String): Option[Node] = None

    override def iterator: Iterator[(String, Node)] = Iterator.empty

    override def withMeta(meta: Meta): Empty = new Empty(meta)

    override def position: Position = Position.None

  object Empty extends Empty(Meta.Empty)
