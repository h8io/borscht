package borscht.typed

import borscht.typed.types.{DefaultTypes, ValueTypeString}
import borscht.{NodeParser, NodeParserPlainString}

lazy val DefaultValueTypeParser = ValueTypeParser(DefaultTypes)

def createNodeParserValueType(using parser: ValueTypeParser): NodeParser[ValueType] =
  NodeParserPlainString andThen parser

def createNodeParserTypedValue(using parser: ValueTypeParser): NodeParser[TypedValue] =
  NodeParserPlainString andThen { value =>
    value.split(":", 2) match
      case Array(value) => TypedValue(ValueTypeString, value)
      case Array(typeDef, value) =>
        val `type` = parser(typeDef)
        TypedValue(`type`, `type`(value))
      case _ => throw IllegalStateException("It should not happen")
  }
