package borscht.typed.valueparser

import borscht.typed.ValueParser

private[valueparser] trait Parser extends (Event => Parser):
  protected def parse: PartialFunction[Event, Parser]

  final def apply(event: Event): Parser =
    (parse orElse { case unexpected => throw UnexpectedEventException(unexpected) })(event)

  def result: Option[ValueParser] = None
