package borscht.typed.valueparser

import borscht.typed.{ValueParser, ValueType}

private[valueparser] final class ParametersParser(parent: Parser,
                                                  `type`: ValueType,
                                                  types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Option[Parser]] = {
    case Event.TypeListStart(_) => Some(ValueTypeListParser(this, types))
    case event: (Event.TypeListEnd | Event.TypeListSeparator | Event.End) =>
      parent(instantiate(event, `type`, Nil)) flatMap (_(event))
    case event@Event.TypeParameters(parameters, position) => parent(instantiate(event, `type`, parameters))
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

private def instantiate(event: Event, `type`: ValueType, parameters: List[ValueParser]): Event.ValueParser =
  `type`.parser(parameters) match
    case Right(parser) => Event.ValueParser(parser, event.position)
    case Left(message) => throw ValueParserInstantiationException(s"Value parser instantiation error: $message", event)
