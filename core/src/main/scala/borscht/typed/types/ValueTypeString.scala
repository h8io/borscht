package borscht.typed.types

import borscht.Node
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

object ValueTypeString extends ValueTypeParameterless with StringParser[String]:
  override def apply(node: Node): String = node.parse[String]

  override def parse(value: String): String = value
