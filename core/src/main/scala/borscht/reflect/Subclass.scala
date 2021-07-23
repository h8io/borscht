package borscht.reflect

import scala.reflect.ClassTag

case class Subclass[T](`class`: Class[? <: T], superclass: Class[T])

object Subclass:
  def apply[T: ClassTag](name: String): Subclass[T] = apply[T](Class.forName(name))

  def apply[T](`class`: Class[?])(using tag: ClassTag[T]) =
    val superclass = tag.runtimeClass.asInstanceOf[Class[T]]
    new Subclass(`class`.asSubclass(superclass), superclass)
