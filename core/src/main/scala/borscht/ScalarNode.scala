package borscht

trait ScalarNode(using recipe: Recipe) extends IterableNode:
  def unwrapped: AnyRef
  
  def asString: String = unwrapped.toString

  override def iterator: Iterator[Node] = Iterator(this)

  override def toString: String = s"${getClass.getName}($unwrapped)"
