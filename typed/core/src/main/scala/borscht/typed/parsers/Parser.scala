package borscht.typed.parsers

private[typed] trait Parser:
  def apply(event: Event): Parser

private[parsers] trait UpdatableParser[T] extends Parser:
  def update(value: T): Unit
