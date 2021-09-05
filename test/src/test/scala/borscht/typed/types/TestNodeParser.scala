package borscht.typed.types

import borscht.{Node, NodeParser, ScalarNode}
import borscht.typed.Ref

case class TestNodeParser(name: String, parameters: List[NodeParser[Ref[?]]]) extends NodeParser[Ref[?]]:
  override def apply(node: Node): Ref[AnyRef] = (node: @unchecked) match
    case scalar: ScalarNode => Ref(classOf[String], s"$name:${scalar.value}")
