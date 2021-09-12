package borscht.typed.parser

import borscht.NodeParser
import borscht.typed.Ref

private[parser] trait Parser extends (Event => Parser):
  protected def parse: PartialFunction[Event, Parser]

  final def apply(event: Event): Parser =
    (parse orElse { case unexpected => throw UnexpectedEventException(unexpected) })(event)

  def result: Option[NodeParser[Ref[?]]] = None
