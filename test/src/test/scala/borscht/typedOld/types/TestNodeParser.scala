package borscht.typedOld.types

import borscht.{Node, NodeParser, ScalarNode}

case class TestNodeParser(name: String, parameters: List[NodeParser[?]]) extends NodeParser[?]:
  override def apply(node: Node): AnyRef = (node: @unchecked) match
    case scalar: ScalarNode => s"$name:${scalar.value}"
