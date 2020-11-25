package borscht

trait IterableNode(using recipe: Recipe) extends Node with Iterable[Node]
