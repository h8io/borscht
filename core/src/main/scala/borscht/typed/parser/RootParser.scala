package borscht.typed.parser

import borscht.NodeParser
import borscht.typed.{Ref, RefType}

private[typed] final class RootParser(types: Map[String, RefType]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case event: Event.TypeName => TypeParser(this, types)(event)
    case Event.NodeParserRef(result, _) => AfterParser(result)

private[parser] final class AfterParser(_result: NodeParser[Ref[?]]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case _: (Event.None | Event.End) => this

  override def result: Option[NodeParser[Ref[?]]] = Some(_result)
