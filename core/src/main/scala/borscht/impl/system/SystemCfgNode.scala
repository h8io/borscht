package borscht.impl.system

import borscht.*

import scala.annotation.tailrec

private[system] class SystemCfgNode(children: Map[String, Node], val meta: Meta = Meta.Empty) extends CfgNode:
  export children.{iterator, get as child}

  override def withMeta(meta: Meta): CfgNode = new SystemCfgNode(
    children map { case (key, node) =>
      key -> node.withMeta(meta)
    },
    meta
  )

  override def position: Position = SystemPosition

  override def toString: String = children map (_ + " -> " + _) mkString ("{", ", ", "}")

object SystemCfgNode:
  def apply(): CfgNode =
    val builder = SystemCfgNodeBuilder()
    for (key, value) <- sys.props do append(builder, key.split('.').iterator, value)
    builder.result

  @tailrec
  private def append(builder: SystemCfgNodeBuilder, ref: Iterator[String], value: String): Unit =
    val key = ref.next
    if ref.hasNext then
      builder.get(key) match
        case Some(nested: SystemCfgNodeBuilder) =>
          append(nested, ref, value)
        case _ =>
          val nested = SystemCfgNodeBuilder()
          builder += (key -> nested)
          append(nested, ref, value)
    else
      builder.get(key) match
        case Some(_: SystemCfgNodeBuilder) =>
        case _                             => builder += (key -> SystemScalarNode(value))
