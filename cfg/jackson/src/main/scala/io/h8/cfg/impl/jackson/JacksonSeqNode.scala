package io.h8.cfg.impl.jackson

import com.fasterxml.jackson.databind.node.ArrayNode
import io.h8.cfg.{SeqNode, Node, Factory}

import scala.jdk.CollectionConverters._

private[jackson] final class JacksonSeqNode(node: ArrayNode, src: JacksonSource)
  extends SeqNode with JacksonNode(node, src):

  override def iterator: Iterator[Node] = node.elements.asScala map wrap(src)
