package borscht.typed

import scala.reflect.ClassTag

final class Ref[T](val `class`: Class[? <: T], _value: => T):
  lazy val value: T = _value

  def cast[R](using tag: ClassTag[R]): Ref[R] =
    val r = tag.runtimeClass
    if (r.isAssignableFrom(`class`)) this.asInstanceOf[Ref[R]]
    else BoxMap.get(`class`) collect {
      case (a, f) if r.isAssignableFrom(a) => Ref(a.asInstanceOf[Class[? <: R]], f.asInstanceOf[T => R](value))
    } getOrElse {
      throw ClassCastException(s"${tag.runtimeClass} is not assignable from ${`class`}")
    }

  def map[R](f: T => R)(using tag: ClassTag[R]): Ref[R] = Ref(f(value))

object Ref:
  def apply[T](value: => T)(using tag: ClassTag[T]): Ref[T] = Ref(tag.runtimeClass.asInstanceOf[Class[T]], value)

  def apply[T](`class`: Class[? <: T], value: => T): Ref[T] = new Ref(`class`, value)
