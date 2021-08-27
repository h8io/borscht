package borscht.typed.parser

import borscht.NodeParser
import borscht.typed.ValueType

private[typed] final class RootParser(types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case event: Event.TypeName => TypeParser(this, types)(event)
    case Event.NodeParser(result, _) => AfterParser(result)

private[parser] final class AfterParser(_result: NodeParser[?]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case _: (Event.None | Event.End) => this

  override def result: Option[NodeParser[?]] = Some(_result)
