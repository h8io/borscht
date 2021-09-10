package borscht.typed.types

import borscht.{Node, NodeParser, ScalarNode}
import borscht.typed.{Ref, RefObj}

case class TestNodeParser(name: String, parameters: List[NodeParser[Ref[?]]]) extends NodeParser[Ref[?]]:
  override def apply(node: Node): RefObj[String] = (node: @unchecked) match
    case scalar: ScalarNode => RefObj[String](
      if (parameters.isEmpty) s"$name:${scalar.value}"
      else parameters map (_ (node).value) mkString(s"$name:[", ", ", "]"))
