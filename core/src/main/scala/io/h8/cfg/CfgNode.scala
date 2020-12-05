package io.h8.cfg

import io.h8.cfg.parsers.given

import scala.annotation.{tailrec, targetName}

trait CfgNode(using factory: Factory) extends Node with Iterable[(String, Node)]:
  @targetName("merge")
  def ++(that: CfgNode): CfgNode = CfgNode.Merged(this, that)

  final def apply[T: NodeParser](ref: String*): T =
    get[T](ref: _*) getOrElse { throw NodeNotFoundException(ref, position) }

  final def get[T](ref: String*)(using parser: NodeParser[T]): Option[T] = node(ref: _*) map { n =>
    try parser(n) catch { case e: Exception => throw CfgNodeParserException(n.position, e) }
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

  protected def child(key: String): Option[Node]

  override def toString = iterator.mkString(s"${getClass.getName}(", ", ", ")") 

object CfgNode:
  private def merge(optFallback: Option[Node], optMain: Option[Node])(using factory: Factory): Option[Node] =
    (optFallback, optMain) match
      case (Some(fallback: CfgNode), Some(main: CfgNode)) => Some(Merged(fallback, main))
      case (_, None) => optFallback
      case _ => optMain

  private case class Merged(fallback: CfgNode, main: CfgNode)(using factory: Factory) extends CfgNode with Node:
    override protected def child(key: String): Option[Node] = merge(fallback.child(key), main.child(key))

    override def iterator: Iterator[(String, Node)] =
      def updated = (fallback.iterator map { (key, node) =>
        key -> merge(Some(node), main.child(key)).get
      }).toMap
      updated.iterator ++ (main.iterator filterNot { (key, _) => updated.contains(key) })

    override def position: Position = fallback.position + main.position
