package borscht.typed.types

import borscht.parsers.NodeParserString
import borscht.typed.types.CharParser.parse
import borscht.{Node, ScalarNode}

import java.lang.Boolean as jBoolean

private object BooleanParser:
  def parse(node: Node): Boolean = node match
    case scalar: ScalarNode => scalar.value match
      case value: Boolean => value
      case value: jBoolean => value.booleanValue
      case value: Int => value != 0
      case _ => parse(node.parse[String])
    case _ => parse(node.parse[String])

  def parse(value: String): Boolean = value.toBoolean

object ValueTypeBoolean extends ValueTypeParameterless with StringParser[Boolean]:
  override def apply(node: Node): Boolean = BooleanParser.parse(node)

  override def parse(value: String): Boolean = BooleanParser.parse(value)

object ValueTypeJBoolean extends ValueTypeParameterless with StringParser[jBoolean]:
  override def apply(node: Node): jBoolean = BooleanParser.parse(node)

  override def parse(value: String): jBoolean = BooleanParser.parse(value)
