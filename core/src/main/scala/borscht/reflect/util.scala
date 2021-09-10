package borscht.reflect

import borscht.*
import borscht.parsers.given

import java.lang.reflect.Constructor
import scala.reflect.ClassTag

def cast[T](`class`: Class[?])(using tag: ClassTag[T]): Class[? <: T] =
  `class`.asSubclass(tag.runtimeClass.asInstanceOf[Class[T]])
