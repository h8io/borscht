package borscht.impl.typesafe

import borscht.{IterableNode, Node, Recipe}
import com.typesafe.config.ConfigList

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeIterableNode(list: ConfigList)(using recipe: Recipe)
  extends IterableNode with Node with TypesafeNode(list):

  override def iterator: Iterator[Node] = list.iterator.asScala map wrap
