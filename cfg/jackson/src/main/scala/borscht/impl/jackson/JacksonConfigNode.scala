package borscht.impl.jackson

import borscht._
import com.fasterxml.jackson.databind.node.{MissingNode, ObjectNode}

import scala.jdk.CollectionConverters._

private[jackson] final class JacksonConfigNode(node: ObjectNode, src: JacksonSource)(using recipe: Recipe)
  extends ConfigNode with Node with JacksonNode(node, src):

  override def iterator: Iterator[(String, Node)] = node.fields.asScala map { entry =>
    entry.getKey -> wrap(src)(entry.getValue)
  }

  override protected def child(key: String): Option[Node] = node.get(key) match
    case _: MissingNode => None
    case jnode => Some(wrap(src)(jnode))
  
