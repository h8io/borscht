package borscht.typed.types

import borscht.Node 

object ValueTypeNode extends ValueTypeParameterless:
  override def apply(node: Node): Any = node
