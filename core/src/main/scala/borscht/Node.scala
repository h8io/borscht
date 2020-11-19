package borscht

import java.lang.{Boolean => jBoolean}

trait Node:
  def parse[T](implicit parser: Parser[T]): T

  def position: Position

  def toString: String
