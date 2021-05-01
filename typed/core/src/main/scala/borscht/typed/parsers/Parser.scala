package borscht.typed.parsers

private[parsers] trait Parser:
  def apply(event: Event): Parser

private[parsers] trait UpdatableParser[T] extends Parser:
  def update(value: T): Unit
