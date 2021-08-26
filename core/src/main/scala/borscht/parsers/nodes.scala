package borscht.parsers

import borscht.*

given NodeParserScalarNode: PartialNodeParser[ScalarNode] =
  case node: ScalarNode => node

given NodeParserSeqNode: PartialNodeParser[SeqNode] =
  case node: SeqNode => node
  case node =>
    class SingletonSeqNode(val meta: Meta = node.meta) extends SeqNode:
      override def withMeta(meta: Meta): SeqNode = SingletonSeqNode(meta)
      override def iterator = Iterator(node)
      override def position: Position = node.position
    SingletonSeqNode()

given NodeParserCfgNode: PartialNodeParser[CfgNode] =
  case node: CfgNode => node
