package borscht.typed.events

private[typed] trait Parser:
  def apply(event: Event): Parser

private[events] trait UpdatableParser[T] extends Parser:
  def update(value: T): Unit
