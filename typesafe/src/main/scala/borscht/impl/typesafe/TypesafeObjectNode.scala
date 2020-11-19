package borscht.impl.typesafe

import borscht.{BorschtParserException, Node, ObjectNode, Parser}
import com.typesafe.config.Config

import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeObjectNode(private val uc: Config)
  extends ObjectNode with TypesafeNode(uc.root):

  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> node(e.getValue) }

  def opt[T: Parser](path: String): Option[T] =
    if (uc.hasPath(path)) Some {
      val n = node(uc.getValue(path))
      try n.parse[T] catch { case e: Exception => throw BorschtParserException(n.position, e) }
    } else None
