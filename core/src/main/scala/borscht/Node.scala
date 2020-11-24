package borscht

import java.lang.{Boolean => jBoolean}

trait Node(using recipe: Recipe):
  given Recipe = recipe
  
  def parse[T](implicit parser: Parser[T]): T

  def position: Position

  def toString: String
