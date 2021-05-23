package borscht.impl.jackson

import borscht.{Node, SeqNode}
import com.fasterxml.jackson.databind.node.ArrayNode

import scala.jdk.CollectionConverters._

private[jackson] final class JacksonSeqNode(node: ArrayNode, src: JacksonSource)
  extends SeqNode with JacksonNode(node, src):

  override def iterator: Iterator[Node] = node.elements.asScala map wrap(src)
