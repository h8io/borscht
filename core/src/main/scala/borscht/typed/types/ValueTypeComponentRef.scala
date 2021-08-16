package borscht.typed.types

import borscht.Node
import borscht.parsers.NodeParserComponentRef
import borscht.reflect.ComponentRef

object ValueTypeComponentRef extends ValueTypeParameterless:
  def apply(node: Node): Any = node.parse[ComponentRef[Any]].get
