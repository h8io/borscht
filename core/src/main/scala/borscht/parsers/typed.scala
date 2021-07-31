package borscht.parsers

import borscht.NodeParser
import borscht.typed.{BaseNodeParserValueRef, ValueRef}

given NodeParserValueRef: NodeParser[ValueRef] = { case node =>
  (node.meta.nodeParserValueRef map (_ orElse BaseNodeParserValueRef) getOrElse BaseNodeParserValueRef)(node)
}
