package io.h8.cfg.parsers

import io.h8.cfg._

given NodeParserScalarNode as NodeParser[ScalarNode] = { case node: ScalarNode => node }

given NodeParserIterableNode as NodeParser[IterableNode] =
  case node: IterableNode => node
  case node => new IterableNode:
    override def iterator = Iterator(node)
    override def position: Position = node.position


given NodeParserCfgNode as NodeParser[CfgNode] = { case node: CfgNode => node }
