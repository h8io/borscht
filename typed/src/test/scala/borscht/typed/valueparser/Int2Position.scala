package borscht.typed.valueparser

given Conversion[Int, Position] with
  def apply(i: Int): Position = Position(i)
