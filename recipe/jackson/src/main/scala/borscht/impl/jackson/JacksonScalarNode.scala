package borscht.impl.jackson

import borscht.{Meta, NodeParserException, ScalarNode}
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.*

private[jackson] object JacksonScalarNode:
  def apply(node: ValueNode, src: JacksonSource, meta: Meta): ScalarNode =
    val unwrapped = node match
      case node: BinaryNode  => node.binaryValue
      case node: BooleanNode => Boolean.box(node.booleanValue)
      case _: NullNode       => None
      case node: NumericNode => node.numberValue
      case node: POJONode    => node.getPojo
      case node: TextNode    => node.textValue
      case node              => throw NodeParserException(s"Unsupported scalar node: $node", src)
    new JacksonScalarNode(unwrapped, node, src, meta)

final private[jackson] class JacksonScalarNode(val value: AnyRef, node: JsonNode, src: JacksonSource, val meta: Meta)
    extends ScalarNode
    with JacksonNode(node, src):
  override def withMeta(meta: Meta): ScalarNode = new JacksonScalarNode(value, node, src, meta)
