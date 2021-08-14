package borscht.typed.types

import borscht.Node 

final class ValueTypeNode extends ValueTypeParameterless:
  override def apply(node: Node): Any = node