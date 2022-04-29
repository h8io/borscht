package borscht.impl.jackson

import borscht.{CfgNode, Meta, Node}
import com.fasterxml.jackson.databind.node.{MissingNode, ObjectNode}

import scala.jdk.CollectionConverters.*

final private[jackson] class JacksonCfgNode(node: ObjectNode, src: JacksonSource, val meta: Meta)
    extends CfgNode
    with JacksonNode(node, src):
  override def withMeta(meta: Meta): CfgNode = JacksonCfgNode(node, src, meta)

  override def iterator: Iterator[(String, Node)] = node.fields.asScala map { entry =>
    entry.getKey -> wrap(src, meta)(entry.getValue)
  }

  override def child(key: String): Option[Node] = node.get(key) match
    case _: MissingNode => None
    case null           => None
    case jnode          => Some(wrap(src, meta)(jnode))
