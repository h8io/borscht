package borscht.parsers

import borscht.{CfgNodeParserException, Node, NodeParser}
import borscht.typed.*

given NodeParserValueParser: NodeParser[ValueParser] with
  override def isDefinedAt(node: Node): Boolean = node.meta.nodeParserValueParser match
    case Some(parser) => parser.isDefinedAt(node)
    case _ => false

  override def apply(node: Node): ValueParser = node.meta.nodeParserValueParser match
    case Some(parser) => parser(node)
    case None => throw CfgNodeParserException("nodeParserValueParser is not defined in meta", node.position)

given NodeParserValueRef: NodeParser[ValueRef] = { case node =>
  (node.meta.nodeParserValueRef map (_ orElse BaseNodeParserValueRef) getOrElse BaseNodeParserValueRef)(node)
}
