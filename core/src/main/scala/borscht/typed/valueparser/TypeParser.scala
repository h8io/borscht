package borscht.typed.valueparser

import borscht.typed.{ValueParser, ValueType}

private[valueparser] class TypeParser(parent: Parser, types: Map[String, ValueType]) extends Parser:
  override def parse: PartialFunction[Event, Option[Parser]] = {
    case Event.TypeName(value, _) => types.get(value) map (ParametersParser(parent, _, types))
  }
