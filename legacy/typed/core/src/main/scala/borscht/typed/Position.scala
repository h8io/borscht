package borscht.typed

opaque type Position = Int

private[typed] object Position:
  def apply(i: Int): Position = i
