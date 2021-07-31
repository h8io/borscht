package borscht.typed.typeparser

given Conversion[Int, Position] with
  def apply(i: Int): Position = Position(i)
