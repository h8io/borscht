package borscht

trait ScalarNode extends Node:
  def value: AnyRef
  
  def asString: String = value.toString

  override def withMeta(meta: Meta): ScalarNode
  
  override def toString: String = s"${getClass.getName}($value)"
