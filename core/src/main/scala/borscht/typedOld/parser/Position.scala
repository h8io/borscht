package borscht.typedOld.parser

opaque type Position = Int

private[parser] object Position:
  def apply(i: Int): Position = i
