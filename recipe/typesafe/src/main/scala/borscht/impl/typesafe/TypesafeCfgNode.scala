package borscht.impl.typesafe

import borscht.{CfgNode, Node}
import com.typesafe.config.{Config, ConfigObject, ConfigValue}

import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeCfgNode(uc: ConfigObject) extends CfgNode with TypesafeNode(uc):
  def this(cfg: Config) = this(cfg.root)

  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> wrap(e.getValue) }

  override def child(key: String): Option[Node] = Option(uc.get(key)) map wrap
