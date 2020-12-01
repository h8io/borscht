package borscht.impl.jackson

import borscht._
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node._

private[jackson] trait JacksonNode(node: JsonNode, src: JacksonSource):
  self: Node =>

  override def position: Position = src
  
  override def toString: String = s"${getClass.getName}(${node.toString})"

private[jackson] def wrap(src: JacksonSource)(using recipe: Recipe): JsonNode => Node =
  case obj: ObjectNode => JacksonConfigNode(obj, src)
  case array: ArrayNode => JacksonIterableNode(array, src)
  case value: ValueNode => JacksonScalarNode(value, src)
  case node => throw new BorschtNodeParserException(s"Unsupported Jackson node: $node", src)
