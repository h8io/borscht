package borscht

import java.lang.{Boolean => jBoolean}

trait Node(using recipe: Recipe):
  given Recipe = recipe
  
  def position: Position

  def toString: String
