package borscht.typed.parser

import borscht.NodeParser
import borscht.typed.{Ref, RefType}

final private[parser] class ParametersParser(parent: Parser, `type`: RefType, types: Map[String, RefType])
    extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case Event.TypeListStart(_) => RefTypeListParser(this, types)
    case event: (Event.TypeListEnd | Event.TypeListSeparator | Event.End) =>
      parent(instantiate(event, `type`, Nil))(event)
    case event @ Event.TypeParameters(parameters, position) => parent(instantiate(event, `type`, parameters))

private class RefTypeListParser(parent: ParametersParser, types: Map[String, RefType]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] = {
    case event: Event.TypeListEnd => parent(Event.TypeParameters(Nil, event.position))
    case event: Event.TypeName =>
      val tailParser = RefTypeListTailParser(parent, types)
      TypeParser(tailParser, types)(event)
  }

private class RefTypeListTailParser(parent: ParametersParser, types: Map[String, RefType]) extends Parser:
  private val builder = List.newBuilder[NodeParser[Ref[?]]]

  override def parse: PartialFunction[Event, Parser] =
    case Event.TypeListEnd(position) => parent(Event.TypeParameters(builder.result, position))
    case Event.NodeParserRef(parser, _) =>
      builder += parser
      this
    case Event.TypeListSeparator(_) => TypeParser(this, types)
    case _: Event.None              => this

private def instantiate(event: Event, `type`: RefType, parameters: List[NodeParser[Ref[?]]]): Event.NodeParserRef =
  `type`.parser(parameters) match
    case Right(parser) => Event.NodeParserRef(parser, event.position)
    case Left(message) => throw ValueParserInstantiationException(s"Value parser instantiation error: $message", event)
