package borscht.typed.types

import borscht.{Node, NodeParser}

trait ValueTypeInherited[T](implicit parser: NodeParser[T]) extends ValueTypeParameterless:
  final def apply(node: Node): T = parser(node)
