package borscht.impl.typesafe

import borscht.{Engine, IterableNode, Node}
import com.typesafe.config.ConfigList

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeIterableNode(list: ConfigList)
  extends IterableNode with TypesafeNode(list):

  override def iterator: Iterator[Node] = list.iterator.asScala map node
