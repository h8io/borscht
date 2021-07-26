package borscht.reflect

class ComponentRef[T](obj: => T):
  def instance: T = obj
