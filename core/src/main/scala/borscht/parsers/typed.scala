package borscht.parsers

import borscht.*
import borscht.typed.*

given NodeParserValueParser: NodeParser[ValueParser] =
  case node => parseType(node.parse[String], node.meta.valueTypes)

given NodeParserValueRef: NodeParser[ValueRef] =
  case node => ValueRef(parseValue(node))
