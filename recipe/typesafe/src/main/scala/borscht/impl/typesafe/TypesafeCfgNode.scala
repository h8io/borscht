package borscht.impl.typesafe

import borscht.{CfgNode, Meta, Node}
import com.typesafe.config.{Config, ConfigObject, ConfigValue}

import scala.annotation.tailrec
import scala.jdk.CollectionConverters.*

private[typesafe] final class TypesafeCfgNode(uc: ConfigObject, val meta: Meta) extends CfgNode with TypesafeNode(uc):
  def this(cfg: Config) = this(cfg.root, Meta.Empty)

  override def withMeta(meta: Meta): CfgNode = TypesafeCfgNode(uc, meta)
  
  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> wrap(e.getValue, meta) }

  override def child(key: String): Option[Node] = Option(uc.get(key)) map (wrap(_, meta))
