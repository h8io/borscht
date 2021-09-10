package borscht.typed.types

import borscht.parsers.given
import borscht.typed.*
import borscht.{Node, NodeParser}

import scala.reflect.ClassTag

trait RefTypePrimitive[T <: Primitive](ref: T => RefPrimitive[T])
                                      (using parser: NodeParser[T], tag: ClassTag[T]) extends RefTypeParameterless: 
  override def apply(node: Node): RefPrimitive[T] = ref(parser(node))

object RefTypeBoolean extends RefTypePrimitive[Boolean](RefBoolean(_))

object RefTypeByte extends RefTypePrimitive[Byte](RefByte(_))

object RefTypeChar extends RefTypePrimitive[Char](RefChar(_))

object RefTypeDouble extends RefTypePrimitive[Double](RefDouble(_))

object RefTypeFloat extends RefTypePrimitive[Float](RefFloat(_))

object RefTypeInt extends RefTypePrimitive[Int](RefInt(_))

object RefTypeLong extends RefTypePrimitive[Long](RefLong(_))

object RefTypeShort extends RefTypePrimitive[Short](RefShort(_))
