package borscht.typed

import borscht.{CfgNode, Node, ScalarNode, SeqNode}

object BaseValueParser extends ValueParser:
  override def apply(node: Node): Any = node match
    case cfg: CfgNode => cfg
    case seq: SeqNode => seq
    case scalar: ScalarNode => scalar.value
