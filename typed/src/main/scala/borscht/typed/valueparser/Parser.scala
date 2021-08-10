package borscht.typed.valueparser

import borscht.typed.ValueParser

private[valueparser] trait Parser extends (Event => Option[Parser]):
  protected def parse: PartialFunction[Event, Option[Parser]]

  final def apply(event: Event): Option[Parser] =
    (parse orElse { case unexpected => throw UnexpectedEventException(unexpected) })(event)

  def result: Option[ValueParser] = None