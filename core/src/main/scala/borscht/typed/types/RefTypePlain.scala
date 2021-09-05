package borscht.typed.types

import borscht.parsers.NodeParserString
import borscht.typed.Ref
import borscht.{Node, NodeParser, ScalarNode}

import scala.reflect.ClassTag

trait RefTypePlain[T](using tag: ClassTag[T]) extends RefTypeParameterless:
  override def apply(node: Node): Ref[T] = Ref(node match
    case scalar: ScalarNode => tag.unapply(scalar.value) getOrElse parse(scalar.asString)
    case _ => parse(node.as[String]))

  protected def parse(value: String): T
