package borscht.typed.types

import borscht.Node
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

final class ValueTypeString extends ValueTypeParameterless with StringParser[String]:
  override def parser(nothing: Unit): ValueParser = _.parse[String]

  override def parse(value: String): String = value

val ValueParserString: Either[String, ValueParser] = ValueTypeString()(Nil)
