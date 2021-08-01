package borscht.typed.valueparser

opaque type Position = Int

private[valueparser] object Position:
  def apply(i: Int): Position = i
