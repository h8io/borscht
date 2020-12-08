package io.h8.cfg.impl.jackson

import com.fasterxml.jackson.databind.node.ArrayNode
import io.h8.cfg.{IterableNode, Node, Factory}

import scala.jdk.CollectionConverters._

private[jackson] final class JacksonIterableNode(node: ArrayNode, src: JacksonSource)
  extends IterableNode with JacksonNode(node, src):

  override def iterator: Iterator[Node] = node.elements.asScala map wrap(src)
