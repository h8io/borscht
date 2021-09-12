package borscht.typed.types

import borscht.parsers.NodeParserString
import borscht.typed.{Ref, RefObj}
import borscht.{Node, NodeParser, ScalarNode}

import scala.reflect.ClassTag

trait RefTypePlain[T <: AnyRef](using tag: ClassTag[T]) extends RefTypeParameterless:
  override def apply(node: Node): Ref[T] = RefObj[T](node match
    case scalar: ScalarNode => tag.unapply(scalar.value) getOrElse parse(scalar.asString)
    case _ => parse(node.as[String]))

  protected def parse(value: String): T
