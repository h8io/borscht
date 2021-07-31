package borscht.typed.typeparser

opaque type Position = Int

private[typeparser] object Position:
  def apply(i: Int): Position = i
