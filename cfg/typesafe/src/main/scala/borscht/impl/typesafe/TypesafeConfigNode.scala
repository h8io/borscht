package borscht.impl.typesafe

import borscht._
import com.typesafe.config.Config

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeConfigNode(uc: Config)(using recipe: Recipe)
  extends ConfigNode with TypesafeNode(uc.root) with Node:

  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> wrap(e.getValue) }

  override def node(path: String): Option[Node] = if (uc.hasPath(path)) Some(wrap(uc.getValue(path))) else None
