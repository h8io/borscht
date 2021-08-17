package borscht.typed.valueparser

import borscht.typed.{ValueParser, ValueType}

private[valueparser] final class RootParser(types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case event: Event.TypeName => TypeParser(this, types)(event)
    case Event.ValueParser(result, _) => AfterParser(result)

private[valueparser] final class AfterParser(_result: ValueParser) extends Parser:
  override protected def parse: PartialFunction[Event, Parser] =
    case _: (Event.None | Event.End) => this

  override def result: Option[ValueParser] = Some(_result)
