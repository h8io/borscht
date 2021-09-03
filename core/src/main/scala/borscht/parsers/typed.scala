package borscht.parsers

import borscht.*
import borscht.typedOld.*

given NodeParserNodeParser: NodeParser[NodeParser[?]] = node => parseType(node.as[String], node.meta.valueTypes)

given NodeParserValueRef: NodeParser[ValueRef] = node => ValueRef(parseValue(node))
