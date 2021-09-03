package borscht.typedOld.types

import borscht.{Node, NodeParser}
import borscht.parsers.NodeParserString
import borscht.virtual.VirtualScalarNode

object ValueTypeEnv extends ValueTypeWithOptionalParameter:
  override protected def create(parser: NodeParser[?]): NodeParser[?] = new NodeParser[?]:
    override def apply(node: Node): Any = parser(new VirtualScalarNode(sys.env(node.as[String]), node))

object ValueTypeProp extends ValueTypeWithOptionalParameter:
  override protected def create(parser: NodeParser[?]): NodeParser[?] = new NodeParser[?]:
    override def apply(node: Node): Any = parser(new VirtualScalarNode(sys.props(node.as[String]), node))
