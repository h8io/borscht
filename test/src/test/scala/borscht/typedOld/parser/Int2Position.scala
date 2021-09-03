package borscht.typedOld.parser

import borscht.typedOld.parser.Position

given Conversion[Int, Position] with
  def apply(i: Int): Position = Position(i)
