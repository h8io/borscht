package borscht.typedOld.parser

import borscht.NodeParser
import borscht.typedOld.ValueType

private[parser] final class ParametersParser(parent: Parser,
                                             `type`: ValueType,
                                             types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case Event.TypeListStart(_) => ValueTypeListParser(this, types)
    case event: (Event.TypeListEnd | Event.TypeListSeparator | Event.End) =>
      parent(instantiate(event, `type`, Nil))(event)
    case event@Event.TypeParameters(parameters, position) => parent(instantiate(event, `type`, parameters))

private class ValueTypeListParser(parent: ParametersParser, types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] = {
    case event: Event.TypeListEnd => parent(Event.TypeParameters(Nil, event.position))
    case event: Event.TypeName =>
      val tailParser = ValueTypeListTailParser(parent, types)
      TypeParser(tailParser, types)(event)
  }

private class ValueTypeListTailParser(parent: ParametersParser, types: Map[String, ValueType]) extends Parser:
  private val builder = List.newBuilder[NodeParser[?]]

  override def parse: PartialFunction[Event, Parser] =
    case Event.TypeListEnd(position) => parent(Event.TypeParameters(builder.result, position))
    case Event.NodeParser(parser, _) =>
      builder += parser
      this
    case Event.TypeListSeparator(_) => TypeParser(this, types)
    case _: Event.None => this

private def instantiate(event: Event, `type`: ValueType, parameters: List[NodeParser[?]]): Event.NodeParser =
  `type`.parser(parameters) match
    case Right(parser) => Event.NodeParser(parser, event.position)
    case Left(message) => throw ValueParserInstantiationException(s"Value parser instantiation error: $message", event)
