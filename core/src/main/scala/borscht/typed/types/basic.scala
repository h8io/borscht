package borscht.typed.types

import borscht.parsers.{NodeParserRef, NodeParserString}
import borscht.typed.{AbstractRefType, Ref, RefObj}
import borscht.{Node, NodeParserException}

import scala.reflect.ClassTag

object RefTypeAny extends RefTypeParameterless:
  override def apply(node: Node): Ref[Any] = node.as[Ref[Any]]

object RefTypeNode extends RefTypeParameterless:
  override def apply(node: Node): Ref[? <: Node] = RefObj(node)(using ClassTag(node.getClass))

