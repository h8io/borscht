package borscht.typed.types

import borscht.ScalarNode
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

private val EncodedCharPattern = raw"\\u([\d+A-Fa-f]{4})".r

final class ValueTypeChar extends ValueTypeParameterless with StringParser[Char]:
  override protected def parser(nothing: Unit): ValueParser = node => node match
    case scalar: ScalarNode => scalar.value match
      case value: Char => value
      case value: Int => value.toChar
      case _ => parse(node.parse[String])
    case _ => parse(node.parse[String])

  override def parse(value: String): Char = if (value.length == 1) value.head else value match {
    case EncodedCharPattern(code) => Integer.parseInt(code, 16).toChar
    case _ => throw MatchError(value)
  }