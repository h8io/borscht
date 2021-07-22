package borscht.impl.system

import borscht._

private[system] class SystemScalarNode(override val value: String, val meta: Meta = Meta.Empty) extends ScalarNode:
  override def withMeta(meta: Meta): ScalarNode = SystemScalarNode(value, meta)

  override def position: Position = SystemPosition

  override def toString: String = value
