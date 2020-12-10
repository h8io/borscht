package io.h8.cfg.impl.system

import io.h8.cfg.{Position, ScalarNode}

private[system] class SystemScalarNode(override val unwrapped: String) extends ScalarNode:
  def position: Position = SystemPosition

  override def toString: String = unwrapped
