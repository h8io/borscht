package borscht.impl.jackson

import borscht.{Meta, Node, SeqNode}
import com.fasterxml.jackson.databind.node.ArrayNode

import scala.jdk.CollectionConverters.*

private[jackson] final class JacksonSeqNode(node: ArrayNode, src: JacksonSource, val meta: Meta)
  extends SeqNode with JacksonNode(node, src):

  override def withMeta(meta: Meta): SeqNode = JacksonSeqNode(node, src, meta)

  override def iterator: Iterator[Node] = node.elements.asScala map wrap(src, meta)
