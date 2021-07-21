package borscht.impl.system

import borscht._

private[system] class SystemScalarNode(override val unwrapped: String, val meta: Meta = Meta.Empty) extends ScalarNode:
  override def withMeta(meta: Meta): ScalarNode = SystemScalarNode(unwrapped, meta)

  override def position: Position = SystemPosition

  override def toString: String = unwrapped
