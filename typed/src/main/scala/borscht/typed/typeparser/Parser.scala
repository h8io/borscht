package borscht.typed.typeparser

import borscht.typed.ValueParser

private[typeparser] trait Parser extends (Event => Option[Parser]):
  protected def parse: PartialFunction[Event, Option[Parser]]

  final def apply(event: Event): Option[Parser] =
    (parse orElse { case unexpected => throw TypeParserException(unexpected) })(event)

  def result: Option[ValueParser] = None
