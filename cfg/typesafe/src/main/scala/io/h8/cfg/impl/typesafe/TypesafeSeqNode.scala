package io.h8.cfg.impl.typesafe

import com.typesafe.config.ConfigList
import io.h8.cfg.{SeqNode, Node}

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeSeqNode(list: ConfigList) extends SeqNode with TypesafeNode(list):
  override def iterator: Iterator[Node] = list.iterator.asScala map wrap
