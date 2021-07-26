package borscht.reflect

class Component[T](obj: => T):
  def instance: T = obj
