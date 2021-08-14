package borscht.typed

import borscht.{CfgNode, Node, ScalarNode, SeqNode}

object BaseValueParser extends ValueParser:
  override def apply(node: Node): Node = node
