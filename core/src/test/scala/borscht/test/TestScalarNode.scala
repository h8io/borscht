package borscht.test

import borscht._

private[test] class TestScalarNode(val value: Any, val meta: Meta = Meta.Empty) extends ScalarNode with TestNode(value):
  override def withMeta(meta: Meta): ScalarNode = TestScalarNode(value, meta)
