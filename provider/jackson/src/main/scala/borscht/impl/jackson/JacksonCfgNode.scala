package borscht.impl.jackson

import borscht.{CfgNode, Node}
import com.fasterxml.jackson.databind.node.{MissingNode, ObjectNode}

import scala.jdk.CollectionConverters._

private[jackson] final class JacksonCfgNode(node: ObjectNode,
                                            src: JacksonSource) extends CfgNode with JacksonNode(node, src):
  override def iterator: Iterator[(String, Node)] = node.fields.asScala map { entry =>
    entry.getKey -> wrap(src)(entry.getValue)
  }

  override def child(key: String): Option[Node] = node.get(key) match
    case _: MissingNode => None
    case null => None
    case jnode => Some(wrap(src)(jnode))
