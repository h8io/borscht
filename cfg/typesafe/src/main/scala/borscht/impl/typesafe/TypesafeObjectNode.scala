package borscht.impl.typesafe

import borscht.{BorschtParserException, Node, ObjectNode, Parser, Recipe}
import com.typesafe.config.Config

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeObjectNode(uc: Config)(using recipe: Recipe)
  extends ObjectNode with TypesafeNode(uc.root) with Node:

  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> node(e.getValue) }

  def opt[T: Parser](path: String): Option[T] =
    if (uc.hasPath(path)) Some {
      val n = node(uc.getValue(path))
      try n.parse[T] catch { case e: Exception => throw BorschtParserException(n.position, e) }
    } else None
