package borscht.impl.system

import borscht.{Position, ScalarNode}

private[system] class SystemScalarNode(override val unwrapped: String) extends ScalarNode:
  def position: Position = SystemPosition

  override def toString: String = unwrapped
