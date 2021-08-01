package borscht.parsers

import borscht.{CfgNodeParserException, Node, NodeParser}
import borscht.typed.*

given NodeParserValueParser: NodeParser[ValueParser] with
  override def isDefinedAt(node: Node): Boolean = node.meta.nodeParserValueParser exists (_.isDefinedAt(node))

  override def apply(node: Node): ValueParser = node.meta.nodeParserValueParser map (_(node)) getOrElse {
    throw CfgNodeParserException("nodeParserValueParser is not defined in meta", node.position)
  }

given NodeParserValueRef: NodeParser[ValueRef] = { case node =>
  (node.meta.nodeParserValueRef map (_ orElse BaseNodeParserValueRef) getOrElse BaseNodeParserValueRef)(node)
}
