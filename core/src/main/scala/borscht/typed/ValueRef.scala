package borscht.typed

import scala.reflect.ClassTag

class ValueRef[T](val `class`: Class[? <: T], _value: => T):
  lazy val value = _value

  def cast[R](using tag: ClassTag[R]): ValueRef[R] =
    if (tag.runtimeClass.isAssignableFrom(`class`)) this.asInstanceOf[ValueRef[R]]
    else throw ClassCastException(s"${tag.runtimeClass} is not assignable from ${`class`}")
