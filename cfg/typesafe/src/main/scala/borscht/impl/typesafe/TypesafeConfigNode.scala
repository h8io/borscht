package borscht.impl.typesafe

import borscht._
import com.typesafe.config.{Config, ConfigObject, ConfigValue}

import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeConfigNode(uc: ConfigObject)(using recipe: Recipe)
  extends ConfigNode with Node with TypesafeNode(uc):

  def this(cfg: Config)(using recipe: Recipe) = this(cfg.root)

  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> wrap(e.getValue) }

  override protected def child(key: String): Option[Node] = Option(uc.get(key)) map wrap
