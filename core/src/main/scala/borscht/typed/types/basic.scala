package borscht.typed.types

import borscht.parsers.NodeParserRef
import borscht.typed.{AbstractRefType, Ref, RefObj}
import borscht.Node

import scala.reflect.ClassTag

object RefTypeAny extends RefTypeParameterless:
  override def apply(node: Node): Ref[Any] = node.as[Ref[Any]]

object RefTypeNode extends RefTypeParameterless:
  override def apply(node: Node): Ref[? <: Node] = RefObj(node)(using ClassTag(node.getClass))
