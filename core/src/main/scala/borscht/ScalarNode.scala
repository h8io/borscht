package borscht

trait ScalarNode(using recipe: Recipe) extends Node:
  override final def parse[T](using parser: Parser[T]): T = parser(this)

  def unwrapped: Any
  
  def asString: String = unwrapped.toString
