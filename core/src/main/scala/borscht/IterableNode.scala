package borscht

trait IterableNode(using recipe: Recipe) extends Node with Iterable[Node]:
  override def toString: String = mkString(s"${getClass.getName}([", ", ", "])")
