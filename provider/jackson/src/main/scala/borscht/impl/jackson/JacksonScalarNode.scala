package borscht.impl.jackson

import borscht.{CfgNodeParserException, ScalarNode}
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node._

private[jackson] object JacksonScalarNode:
  def apply(node: ValueNode, src: JacksonSource): ScalarNode =
    val unwrapped = node match
      case node: BinaryNode => node.binaryValue
      case node: BooleanNode => Boolean.box(node.booleanValue)
      case _: NullNode => None
      case node: NumericNode => node.numberValue
      case node: POJONode => node.getPojo
      case node: TextNode => node.textValue
      case node => throw CfgNodeParserException(s"Unsupported scalar node: $node", src)
    new JacksonScalarNode(unwrapped, node, src)


private[jackson] final class JacksonScalarNode(val unwrapped: AnyRef, node: JsonNode, src: JacksonSource)
  extends ScalarNode with JacksonNode(node, src)
