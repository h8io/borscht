package borscht.impl.typesafe

import borscht._
import com.typesafe.config.Config

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeConfigNode(uc: Config)(using recipe: Recipe)
  extends ConfigNode with TypesafeNode(uc.root) with Node:

  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> node(e.getValue) }

  def opt[T](path: String)(using parser: NodeParser[T]): Option[T] =
    if (uc.hasPath(path)) Some {
      val n = node(uc.getValue(path))
      try parser(n) catch { case e: Exception => throw BorschtNodeParserException(n.position, e) }
    } else None
