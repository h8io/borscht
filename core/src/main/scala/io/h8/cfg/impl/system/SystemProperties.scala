package io.h8.cfg.impl.system

import io.h8.cfg.CfgNode

import scala.annotation.tailrec

object SystemProperties extends CfgNode:
  private val cfg: SystemCfgNode =
    val builder = SystemCfgNodeBuilder()
    for ((key, value) <- sys.props) append(builder, key.split(".").iterator, value)
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
  
  export cfg._
