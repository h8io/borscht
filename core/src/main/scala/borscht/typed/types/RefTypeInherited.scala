package borscht.typed.types

import borscht.{Node, NodeParser}
import borscht.typed.RefObj

import scala.reflect.ClassTag

trait RefTypeInherited[T <: AnyRef](using parser: NodeParser[T], tag: ClassTag[T]) extends RefTypeParameterless:
  override def apply(node: Node): RefObj[T] = RefObj(parser(node))
