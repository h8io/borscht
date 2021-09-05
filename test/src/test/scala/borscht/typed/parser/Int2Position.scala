package borscht.typed.parser

import borscht.typed.parser.Position

given Conversion[Int, Position] with
  def apply(i: Int): Position = Position(i)
