package borscht.test

import borscht.{Meta, Position, ScalarNode}

private[test] case class MockScalarNode(value: Any, meta: Meta, position: Position) extends ScalarNode:
  override def withMeta(meta: Meta): ScalarNode = copy(meta = meta)
