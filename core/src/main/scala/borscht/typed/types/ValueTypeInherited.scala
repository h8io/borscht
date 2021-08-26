package borscht.typed.types

import borscht.typed.AbstractValueType
import borscht.{Node, NodeParser}

trait ValueTypeInherited[T](implicit parser: NodeParser[T]) extends ValueTypeParameterless:
  override protected def create(nothing: Unit): NodeParser[T] = parser

  override def apply(node: Node): T = parser(node)