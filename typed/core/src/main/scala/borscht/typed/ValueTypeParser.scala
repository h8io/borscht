package borscht.typed

import borscht.typed.types.{ValueType, ValueTypeConstructor}

final class ValueTypeParser(types: Map[String, ValueTypeConstructor]):
  def apply(definition: String): ValueType =
    val events = Events(definition)
    val rootParser = RootTypeParser(types)
    val result = events.foldLeft[Parser](rootParser) { (parser, event) => parser(event) }
    if (result == rootParser) rootParser.result
    else throw IllegalStateException("The result parser is not equal to the root parser (it should not happen)")
