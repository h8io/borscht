package borscht.impl.typesafe

import borscht.{Node, Parser}
import com.typesafe.config.{ConfigList, ConfigObject, ConfigRenderOptions, ConfigValue}

private[typesafe] trait TypesafeNode private[typesafe](value: ConfigValue) extends Node :
  def parse[T](implicit parser: Parser[T]): T

  override lazy val position = TypesafePosition(value.origin)

  override def toString: String = getClass.getSimpleName + "(" + value.render(ConfigRenderOptions.concise) + ")"

private[typesafe] def node(value: ConfigValue): Node = value match
  case list: ConfigList => TypesafeIterableNode(list)
  case obj: ConfigObject => TypesafeObjectNode(obj.toConfig)
  case scalar => TypesafeScalarNode(scalar)
