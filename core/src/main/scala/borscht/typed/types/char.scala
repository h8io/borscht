package borscht.typed.types

import borscht.{Node, ScalarNode}
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

private val EncodedCharPattern = raw"\\u([\d+A-Fa-f]{4})".r

private object CharParser:
  def parse(node: Node): Char = node match
    case scalar: ScalarNode => scalar.value match
      case value: Char => value
      case value: Character => value.charValue
      case value: Int => value.toChar
      case _ => parse(node.parse[String])
    case _ => parse(node.parse[String])

  def parse(value: String): Char = if (value.length == 1) value.head else value match
    case EncodedCharPattern(code) => Integer.parseInt(code, 16).toChar
    case _ => throw MatchError(value)

final class ValueTypeChar extends ValueTypeParameterless with StringParser[Char]:
  override def apply(node: Node): Char = CharParser.parse(node)

  override def parse(value: String): Char = CharParser.parse(value)

final class ValueTypeJChar extends ValueTypeParameterless with StringParser[Character]:
  override def apply(node: Node): Character = CharParser.parse(node)

  override def parse(value: String): Character = CharParser.parse(value)
