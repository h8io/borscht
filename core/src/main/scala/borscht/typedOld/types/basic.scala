package borscht.typedOld.types

import borscht.parsers.given
import borscht.typedOld
import borscht.typedOld.{AbstractValueType, ValueRef}
import borscht.{Node, NodeParser, SeqNode}

import java.lang.Boolean as jBoolean

object ValueTypeAny extends ValueTypeParameterless:
  override def apply(node: Node): Any = node.as[ValueRef].get


object ValueTypeString extends ValueTypeInherited[String]


object ValueTypeBoolean extends ValueTypeInherited[Boolean]

object ValueTypeJBoolean extends ValueTypeParameterless:
  override def apply(node: Node): jBoolean = node.as[Boolean]


object ValueTypeChar extends ValueTypeInherited[Char]

object ValueTypeJChar extends ValueTypeParameterless:
  override def apply(node: Node): Character = node.as[Char]
