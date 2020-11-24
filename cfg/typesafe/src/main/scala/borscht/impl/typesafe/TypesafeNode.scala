package borscht.impl.typesafe

import borscht.{Node, Parser}
import com.typesafe.config.{ConfigList, ConfigObject, ConfigRenderOptions, ConfigValue}

private[typesafe] trait TypesafeNode(value: ConfigValue) extends Node:
  override lazy val position = TypesafePosition(value.origin)

  override def toString: String = getClass.getSimpleName + "(" + value.render(ConfigRenderOptions.concise) + ")"

private[typesafe] def node(value: ConfigValue): Node = value match
  case list: ConfigList => TypesafeIterableNode(list)
  case obj: ConfigObject => TypesafeObjectNode(obj.toConfig)
  case scalar => TypesafeScalarNode(scalar)
