package borscht.typed

import borscht.Node
import borscht.parsers.NodeParserValueRef

class ValueRefs(underlying: Iterator[Node]) extends Iterator[Any]:
  def this(refs: IterableOnce[Node]) = this(refs.iterator)
  
  override def hasNext: Boolean = underlying.hasNext

  override def next(): Any = underlying.next.as[ValueRef].get
