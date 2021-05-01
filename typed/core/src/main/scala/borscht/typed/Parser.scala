package borscht.typed

private[typed] trait Parser:
  def apply(event: Event): Parser

private[typed] trait UpdatableParser[T] extends Parser:
  def update(value: T): Unit
