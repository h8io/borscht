package borscht.impl.jackson

import borscht.{Meta, Node, NodeParserException, Position}
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.*

private[jackson] trait JacksonNode(node: JsonNode, src: JacksonSource):
  self: Node =>

  override def position: Position = src

  override def toString: String = s"${getClass.getName}(${node.toString})"

private[jackson] def wrap(src: JacksonSource, meta: Meta): JsonNode => Node =
  case obj: ObjectNode  => JacksonCfgNode(obj, src, meta)
  case array: ArrayNode => JacksonSeqNode(array, src, meta)
  case value: ValueNode => JacksonScalarNode(value, src, meta)
  case node             => throw new NodeParserException(s"Unsupported Jackson node: $node", src)
