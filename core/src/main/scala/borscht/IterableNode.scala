package borscht

trait IterableNode(using recipe: Recipe) extends Node with Iterable[Node]:
  override final def parse[T](using parser: Parser[T]): T = parser(this)
