package borscht.typed.parser

opaque type Position = Int

private[parser] object Position:
  def apply(i: Int): Position = i
