package io.h8.cfg

trait ScalarNode extends Node:
  def unwrapped: AnyRef
  
  def asString: String = unwrapped.toString
  
  override def toString: String = s"${getClass.getName}($unwrapped)"
