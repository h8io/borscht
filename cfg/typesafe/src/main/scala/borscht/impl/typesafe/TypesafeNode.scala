package borscht.impl.typesafe

import borscht.{Node, Recipe}
import com.typesafe.config.{ConfigList, ConfigObject, ConfigRenderOptions, ConfigValue}

private[typesafe] trait TypesafeNode(value: ConfigValue):
  self: Node =>
  
  override lazy val position = TypesafePosition(value.origin)

  override def toString: String = getClass.getSimpleName + "(" + value.render(ConfigRenderOptions.concise) + ")"

private[typesafe] def wrap(value: ConfigValue)(using recipe: Recipe): Node = value match
  case list: ConfigList => TypesafeIterableNode(list)
  case obj: ConfigObject => TypesafeConfigNode(obj)
  case scalar => TypesafeScalarNode(scalar)
