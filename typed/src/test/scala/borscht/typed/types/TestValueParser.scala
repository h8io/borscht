package borscht.typed.types

import borscht.{Node, ScalarNode}
import borscht.typed.ValueParser

case class TestValueParser(name: String, parameters: List[ValueParser]) extends ValueParser:
  override def isDefinedAt(node: Node): Boolean = node.isInstanceOf[ScalarNode]

  override def apply(node: Node): AnyRef = node match
    case scalar: ScalarNode => s"$name:${scalar.value}"
    case _ => throw MatchError(node)
