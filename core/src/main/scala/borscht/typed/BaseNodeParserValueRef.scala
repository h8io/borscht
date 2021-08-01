package borscht.typed

import borscht.{Node, NodeParser}

object BaseNodeParserValueRef extends NodeParser[ValueRef]:
  override def isDefinedAt(node: Node): Boolean = true

  override def apply(node: Node): ValueRef = ValueRef(BaseValueParser(node))