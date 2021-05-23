package borscht.impl.jackson

import borscht.{CfgNodeParserException, Node, Position}
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node._

private[jackson] trait JacksonNode(node: JsonNode, src: JacksonSource) extends Node:
  override def position: Position = src
  
  override def toString: String = s"${getClass.getName}(${node.toString})"

private[jackson] def wrap(src: JacksonSource): JsonNode => Node =
  case obj: ObjectNode => JacksonCfgNode(obj, src)
  case array: ArrayNode => JacksonSeqNode(array, src)
  case value: ValueNode => JacksonScalarNode(value, src)
  case node => throw new CfgNodeParserException(s"Unsupported Jackson node: $node", src)
