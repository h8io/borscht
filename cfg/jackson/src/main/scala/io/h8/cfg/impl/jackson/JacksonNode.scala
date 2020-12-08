package io.h8.cfg.impl.jackson

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node._
import io.h8.cfg._

private[jackson] trait JacksonNode(node: JsonNode, src: JacksonSource) extends Node:
  override def position: Position = src
  
  override def toString: String = s"${getClass.getName}(${node.toString})"

private[jackson] def wrap(src: JacksonSource): JsonNode => Node =
  case obj: ObjectNode => JacksonCfgNode(obj, src)
  case array: ArrayNode => JacksonIterableNode(array, src)
  case value: ValueNode => JacksonScalarNode(value, src)
  case node => throw new CfgNodeParserException(s"Unsupported Jackson node: $node", src)
