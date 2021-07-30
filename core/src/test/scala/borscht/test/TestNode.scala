package borscht.test

import borscht.*

private[test] trait TestNode(value: Any):
  self: Node =>

  override def position: Position = Position.None

  override def toString: String = s"${getClass.getName}(${value.toString})"

