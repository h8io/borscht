package borscht.typed.test

import borscht.typed.Position

given Conversion[Int, Position] with
  def apply(i: Int): Position = Position(i)
