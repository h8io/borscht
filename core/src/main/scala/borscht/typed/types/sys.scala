package borscht.typed.types

import borscht.Node
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser
import borscht.virtual.VirtualScalarNode

object ValueTypeEnv extends ValueTypeWithOptionalParameter:
  override protected def create(parser: ValueParser): ValueParser = new ValueParser:
    override def apply(node: Node): Any = parser(new VirtualScalarNode(sys.env(node.parse[String]), node))

object ValueTypeProp extends ValueTypeWithOptionalParameter:
  override protected def create(parser: ValueParser): ValueParser = new ValueParser:
    override def apply(node: Node): Any = parser(new VirtualScalarNode(sys.props(node.parse[String]), node))
