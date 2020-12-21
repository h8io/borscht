package borscht.parsers

import borscht.{CfgNode, NodeParser, Position, ScalarNode, SeqNode}

given NodeParserScalarNode: NodeParser[ScalarNode] = { case node: ScalarNode => node }

given NodeParserSeqNode: NodeParser[SeqNode] =
  case node: SeqNode => node
  case node => new SeqNode:
    override def iterator = Iterator(node)
    override def position: Position = node.position


given NodeParserCfgNode: NodeParser[CfgNode] = { case node: CfgNode => node }
