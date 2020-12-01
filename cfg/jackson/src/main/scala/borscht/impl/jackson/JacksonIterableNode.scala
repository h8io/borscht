package borscht.impl.jackson

import borscht._
import com.fasterxml.jackson.databind.node.ArrayNode

import scala.jdk.CollectionConverters._

private[jackson] final class JacksonIterableNode(node: ArrayNode, src: JacksonSource)(using recipe: Recipe)
  extends IterableNode with Node with JacksonNode(node, src):

  override def iterator: Iterator[Node] = node.elements.asScala map wrap(src)
