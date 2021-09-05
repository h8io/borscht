package borscht.typed.types

import borscht.parsers.given
import borscht.typed.{AbstractRefType, Ref}
import borscht.{Node, NodeParser, SeqNode, util}

import java.lang.Boolean as jBoolean

object RefTypeAny extends RefTypeParameterless:
  override def apply(node: Node): Ref[Any] = ??? // node.as[Ref]

object RefTypeNode extends RefTypeParameterless:
  override def apply(node: Node): Ref[? <: Node] = Ref(node.getClass, node)

object ValueTypeString extends RefTypeInherited[String]

object ValueTypeBoolean extends RefTypeInherited[Boolean]

object ValueTypeChar extends RefTypeInherited[Char]
