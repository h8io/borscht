package borscht.typed.types

import borscht.{Node, ScalarNode}
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

import scala.reflect.ClassTag

trait ValueTypePlain[T](using tag: ClassTag[T]) extends ValueTypeParameterless:
  override def apply(node: Node): T = node match
    case scalar: ScalarNode => tag.unapply(scalar.value) getOrElse parse(scalar.asString)
    case _ => parse(node.as[String])

  protected def parse(value: String): T
