package io.h8.cfg.impl.typesafe

import com.typesafe.config.ConfigList
import io.h8.cfg.{IterableNode, Node, Factory}

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeIterableNode(list: ConfigList)(using factory: Factory)
  extends IterableNode with Node with TypesafeNode(list):

  override def iterator: Iterator[Node] = list.iterator.asScala map wrap
