package io.h8.cfg.impl.typesafe

import com.typesafe.config.{ConfigList, ConfigObject, ConfigRenderOptions, ConfigValue}
import io.h8.cfg.{Node, Factory}

private[typesafe] trait TypesafeNode(value: ConfigValue):
  self: Node =>
  
  override lazy val position = TypesafePosition(value.origin)

  override def toString: String = getClass.getSimpleName + "(" + value.render(ConfigRenderOptions.concise) + ")"

private[typesafe] def wrap(value: ConfigValue)(using factory: Factory): Node = value match
  case list: ConfigList => TypesafeIterableNode(list)
  case obj: ConfigObject => TypesafeCfgNode(obj)
  case scalar => TypesafeScalarNode(scalar)
