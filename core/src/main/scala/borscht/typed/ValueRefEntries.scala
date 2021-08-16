package borscht.typed

import borscht.{CfgNode, Node}
import borscht.parsers.NodeParserValueRef

class ValueRefEntries(underlying: Iterator[(String, Node)]) extends Iterator[(String, Any)]:
  def this(entries: IterableOnce[(String, Node)]) = this(entries.iterator)
  
  override def hasNext: Boolean = underlying.hasNext

  override def next(): (String, Any) =
    val (key, node) = underlying.next()
    key -> node.parse[ValueRef].get
