package borscht.test

import borscht.*

private[test] case class MockSeqNode(values: Iterable[?], meta: Meta, position: Position) extends SeqNode:
  override def withMeta(meta: Meta): SeqNode = copy(meta = meta)

  override def iterator: Iterator[Node] = values.iterator.zipWithIndex map { (item, index) =>
    wrap(item, meta, MockSeqPosition(position, index))
  }

private case class MockSeqPosition(position: Position, index: Int) extends Position.Some :
  override def toString: String = s"[$position, index $index]"
