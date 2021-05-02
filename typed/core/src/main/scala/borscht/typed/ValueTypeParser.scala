package borscht.typed

import borscht.typed.events.{Events, Parser, RootParser}

final class ValueTypeParser(types: PartialFunction[String, ValueTypeConstructor]):
  def apply(definition: String): ValueType =
    val events = Events(definition)
    val rootParser = RootParser(types)
    val result = events.foldLeft[Parser](rootParser) { (parser, event) => parser(event) }
    if (result == rootParser) rootParser.result
    else throw IllegalStateException("The result parser is not equal to the root parser (it should not happen)")
