package borscht.typed.events

import borscht.typed.ValueParser

private[typed] trait Parser extends (Event => Option[Parser]):
  protected def parse: PartialFunction[Event, Option[Parser]]

  final def apply(event: Event): Option[Parser] =
    (parse orElse { case unexpected => throw UnexpectedEventException(unexpected) })(event)

  def result: Option[ValueParser] = None
