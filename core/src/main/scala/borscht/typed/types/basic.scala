package borscht.typed.types

import borscht.parsers.given
import borscht.typed.{AbstractRefType, Ref}
import borscht.Node

object RefTypeAny extends RefTypeParameterless:
  override def apply(node: Node): Ref[Any] = ??? // node.as[Ref]

object RefTypeNode extends RefTypeParameterless:
  override def apply(node: Node): Ref[? <: Node] = Ref(node.getClass, node)
