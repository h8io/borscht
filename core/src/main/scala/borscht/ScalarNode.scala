package borscht

trait ScalarNode(using recipe: Recipe) extends IterableNode:
  def unwrapped: Any
  
  def asString: String = unwrapped.toString

  override def iterator: Iterator[Node] = Iterator(this)
