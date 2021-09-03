package borscht.typedOld.types

import borscht.Node
import borscht.parsers.{NodeParserClass, NodeParserComponentRef}
import borscht.reflect.ComponentRef

object ValueTypeClass extends ValueTypeInherited[Class[?]]

object ValueTypeComponentRef extends ValueTypeParameterless:
  def apply(node: Node): Any = node.as[ComponentRef[Any]].get
