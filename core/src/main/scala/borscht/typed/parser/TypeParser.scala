package borscht.typed.parser

import borscht.typed.ValueType

private[parser] class TypeParser(parent: Parser, types: Map[String, ValueType]) extends Parser:
  override def parse: PartialFunction[Event, Parser] =
    case event: Event.TypeName => types.get(event.value) map (ParametersParser(parent, _, types)) getOrElse {
      throw UnknownTypeException(event.value, event)
    }
