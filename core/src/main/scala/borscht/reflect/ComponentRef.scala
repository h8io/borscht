package borscht.reflect

import borscht.Node

import scala.reflect.ClassTag

class ComponentRef[T](obj: => T):
  def get: T = obj
