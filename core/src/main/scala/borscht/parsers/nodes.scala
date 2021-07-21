package borscht.parsers

import borscht._

given NodeParserScalarNode: NodeParser[ScalarNode] = { case node: ScalarNode => node }

given NodeParserSeqNode: NodeParser[SeqNode] =
  case node: SeqNode => node
  case node =>
    class SingletonSeqNode(val meta: Meta = node.meta) extends SeqNode:
      override def withMeta(meta: Meta): SeqNode = SingletonSeqNode(meta)
      override def iterator = Iterator(node)
      override def position: Position = node.position
    SingletonSeqNode()


given NodeParserCfgNode: NodeParser[CfgNode] = { case node: CfgNode => node }
