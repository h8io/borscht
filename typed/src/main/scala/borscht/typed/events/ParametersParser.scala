package borscht.typed.events

import borscht.typed.{ValueParser, ValueType}

private[events] final class ParametersParser(parent: Parser,
                                             `type`: ValueType,
                                             types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Option[Parser]] = {
    case Event.TypeListStart(_) => Some(ValueTypeListParser(this, types))
    case event: (Event.TypeListEnd | Event.TypeListSeparator | Event.End) =>
      parent(Event.ValueParser(`type`(Nil), event.position)) flatMap (_(event))
    case Event.TypeParameters(parameters, position) => parent(Event.ValueParser(`type`(parameters), position))
  }

private class ValueTypeListParser(parent: ParametersParser, types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Option[Parser]] = {
    case event: Event.TypeListEnd => parent(Event.TypeParameters(Nil, event.position))
    case event: Event.TypeName =>
      val tailParser = ValueTypeListTailParser(parent, types)
      TypeParser(tailParser, types)(event)
  }

private class ValueTypeListTailParser(parent: ParametersParser, types: Map[String, ValueType]) extends Parser:
  private val builder = List.newBuilder[ValueParser]

  override def parse: PartialFunction[Event, Option[Parser]] = {
    case Event.TypeListEnd(position) => parent(Event.TypeParameters(builder.result, position))
    case Event.ValueParser(parser, _) =>
      builder += parser
      Some(this)
    case Event.TypeListSeparator(_) => Some(TypeParser(this, types))
    case _: Event.None => Some(this)
  }
