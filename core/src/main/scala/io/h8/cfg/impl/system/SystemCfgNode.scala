package io.h8.cfg.impl.system

import io.h8.cfg.{CfgNode, Node, Position}

import scala.annotation.tailrec

private[system] class SystemCfgNode(children: Map[String, Node]) extends CfgNode:
  export children.{iterator, get => child}

  override def position: Position = SystemPosition

  override def toString: String = children map (_ + " -> " + _) mkString ("{", ", ",  "}")

object SystemCfgNode:
  def apply(): CfgNode =
    val builder = SystemCfgNodeBuilder()
    for ((key, value) <- sys.props) append(builder, key.split('.').iterator, value)
    builder.result

  @tailrec
  private def append(builder: SystemCfgNodeBuilder, ref: Iterator[String], value: String): Unit =
    val key = ref.next
    if (ref.hasNext)
      builder.get(key) match
        case Some(nested: SystemCfgNodeBuilder) =>
          append(nested, ref, value)
        case _ =>
          val nested = SystemCfgNodeBuilder()
          builder += (key -> nested)
          append(nested, ref, value)
    else
      builder.get(key) match
        case Some(nested: SystemCfgNodeBuilder) =>
        case _ => builder += (key -> SystemScalarNode(value))
