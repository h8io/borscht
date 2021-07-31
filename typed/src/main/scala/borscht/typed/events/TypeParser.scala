package borscht.typed.events

import borscht.typed.{ValueParser, ValueType}

private[events] class TypeParser(parent: Parser, types: Map[String, ValueType]) extends Parser:
  override def parse: PartialFunction[Event, Option[Parser]] = {
    case Event.TypeName(value, _) => types.get(value) map (ParametersParser(parent, _, types))
  }
