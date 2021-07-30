package borscht.parsers

import borscht.NodeParser
import borscht.typed.{BaseNodeParserTypedValue, TypedValue}

given NodeParserTypedValue: NodeParser[TypedValue] = { case node =>
  (node.meta.nodeParserTypedValue map (_ orElse BaseNodeParserTypedValue) getOrElse BaseNodeParserTypedValue)(node)
}
