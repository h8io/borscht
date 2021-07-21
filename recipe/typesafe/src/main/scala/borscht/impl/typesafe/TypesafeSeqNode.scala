package borscht.impl.typesafe

import borscht.{Meta, Node, SeqNode}
import com.typesafe.config.ConfigList

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeSeqNode(list: ConfigList, val meta: Meta) extends SeqNode with TypesafeNode(list):
  override def withMeta(meta: Meta): SeqNode = TypesafeSeqNode(list, meta)
  
  override def iterator: Iterator[Node] = list.iterator.asScala map (wrap(_, meta))
