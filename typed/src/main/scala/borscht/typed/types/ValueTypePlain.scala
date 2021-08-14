package borscht.typed.types

import borscht.{Node, ScalarNode}
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

import scala.reflect.ClassTag

trait ValueTypePlain[T](using tag: ClassTag[T]) extends ValueTypeParameterless with StringParser[T]:
  override def apply(node: Node): Any = node match
    case node: ScalarNode => tag.unapply(node.value) getOrElse parse(node.parse[String])
    case node => parse(node.parse[String])
