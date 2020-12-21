package borscht.impl.typesafe

import borscht.{Node, SeqNode}
import com.typesafe.config.ConfigList

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeSeqNode(list: ConfigList) extends SeqNode with TypesafeNode(list):
  override def iterator: Iterator[Node] = list.iterator.asScala map wrap
