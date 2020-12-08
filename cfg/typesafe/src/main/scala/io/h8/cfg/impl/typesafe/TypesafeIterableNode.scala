package io.h8.cfg.impl.typesafe

import com.typesafe.config.ConfigList
import io.h8.cfg.{IterableNode, Node}

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeIterableNode(list: ConfigList) extends IterableNode with TypesafeNode(list):
  override def iterator: Iterator[Node] = list.iterator.asScala map wrap
