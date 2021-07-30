package borscht.impl.jackson

import borscht.{CfgNodeParserException, Meta, Node, Position}
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node._

private[jackson] trait JacksonNode(node: JsonNode, src: JacksonSource):
  self: Node =>
  
  override def position: Position = src
  
  override def toString: String = s"${getClass.getName}(${node.toString})"

private[jackson] def wrap(src: JacksonSource, meta: Meta): JsonNode => Node =
  case obj: ObjectNode => JacksonCfgNode(obj, src, meta)
  case array: ArrayNode => JacksonSeqNode(array, src, meta)
  case value: ValueNode => JacksonScalarNode(value, src, meta)
  case node => throw new CfgNodeParserException(s"Unsupported Jackson node: $node", src)
