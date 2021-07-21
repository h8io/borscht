package borscht

trait ScalarNode extends Node:
  def unwrapped: AnyRef
  
  def asString: String = unwrapped.toString

  override def withMeta(meta: Meta): ScalarNode
  
  override def toString: String = s"${getClass.getName}($unwrapped)"
