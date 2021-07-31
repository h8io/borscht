package borscht.typed.events

import borscht.typed.{ValueParser, ValueType}

private[typed] final class RootParser(types: Map[String, ValueType]) extends Parser:
  override protected def parse: PartialFunction[Event, Option[Parser]] = {
    case event: Event.TypeName => TypeParser(this, types)(event)
    case Event.ValueParser(result, _) => Some(AfterParser(result))
  }

private[typed] final class AfterParser(_result: ValueParser) extends Parser:
  override protected def parse: PartialFunction[Event, Option[Parser]] = {
    case _: (Event.None | Event.End) => Some(this)
  }

  override def result: Option[ValueParser] = Some(_result)
