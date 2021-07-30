package borscht.test

import borscht.*

private[test] class TestSeqNode(value: List[Node], val meta: Meta = Meta.Empty) extends SeqNode with TestNode(value):
  override def withMeta(meta: Meta): SeqNode = TestSeqNode(value map (_.withMeta(meta)), meta)

  override def iterator: Iterator[Node] = value.iterator
