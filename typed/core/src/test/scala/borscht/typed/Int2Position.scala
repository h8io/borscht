package borscht.typed

given Conversion[Int, Position] with
  def apply(i: Int): Position = Position(i)
