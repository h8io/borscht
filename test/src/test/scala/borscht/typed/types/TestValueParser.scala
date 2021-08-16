package borscht.typed.types

import borscht.{Node, ScalarNode}
import borscht.typed.ValueParser

case class TestValueParser(name: String, parameters: List[ValueParser]) extends ValueParser:
  override def apply(node: Node): AnyRef = (node: @unchecked) match
    case scalar: ScalarNode => s"$name:${scalar.value}"
