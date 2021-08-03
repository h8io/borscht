package borscht.reflect

import borscht.CfgNode

trait Superclass:
  def isParameterless: Boolean

  def optCfg: Option[CfgNode]

class ComponentWithParameterlessConstructor() extends Superclass:
  override def isParameterless: Boolean = true

  override def optCfg: Option[CfgNode] = None

class ComponentWithCfgConstructor(cfg: CfgNode) extends Superclass:
  override def isParameterless: Boolean = false

  override val optCfg: Option[CfgNode] = if (cfg == CfgNode.Empty) None else Some(cfg)

class ComponentWithBothConstructors(val cfg: CfgNode, val isParameterless: Boolean) extends Superclass:
  def this() = this(CfgNode.Empty, true)

  def this(cfg: CfgNode) = this(cfg, false)

  override val optCfg: Option[CfgNode] = if (cfg == CfgNode.Empty) None else Some(cfg)

case class ComponentWithoutAppropriateConstructor(value: Integer) extends Superclass:
  override def isParameterless: Boolean = false

  override def optCfg: Option[CfgNode] = None

case class ComponentWithMultipleParameters(cfg: CfgNode, str: String, value: Integer, is: java.lang.Boolean)
