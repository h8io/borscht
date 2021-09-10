package borscht.typed.types

import borscht.{Node, NodeParser}
import borscht.parsers.NodeParserString
import borscht.typed.Ref
import borscht.virtual.VirtualScalarNode

object RefTypeEnv extends RefTypeWithOptionalParameter:
  override protected def create(parser: NodeParser[Ref[?]]): NodeParser[Ref[?]] =
    node => parser(new VirtualScalarNode(sys.env(node.as[String]), node))

object RefTypeProp extends RefTypeWithOptionalParameter:
  override protected def create(parser: NodeParser[Ref[?]]): NodeParser[Ref[?]] =
    node => parser(new VirtualScalarNode(sys.props(node.as[String]), node))
