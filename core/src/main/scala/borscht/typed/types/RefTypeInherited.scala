package borscht.typed.types

import borscht.typed.Ref
import borscht.typedOld.AbstractValueType
import borscht.{Node, NodeParser}

import scala.reflect.ClassTag

trait RefTypeInherited[T](using parser: NodeParser[T], tag: ClassTag[T]) extends RefTypeParameterless:
  override protected def create(nothing: Unit): NodeParser[Ref[T]] = parser andThen (Ref(_))

  override def apply(node: Node): Ref[T] = Ref(parser(node))