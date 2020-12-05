package io.h8.cfg.impl.typesafe

import io.h8.cfg._
import com.typesafe.config.{Config, ConfigObject, ConfigValue}

import scala.annotation.tailrec
import scala.jdk.CollectionConverters._

private[typesafe] final class TypesafeCfgNode(uc: ConfigObject)(using factory: Factory)
  extends CfgNode with Node with TypesafeNode(uc):

  def this(cfg: Config)(using factory: Factory) = this(cfg.root)

  override def iterator: Iterator[(String, Node)] =
    uc.entrySet().iterator.asScala map { e => e.getKey -> wrap(e.getValue) }

  override protected def child(key: String): Option[Node] = Option(uc.get(key)) map wrap
