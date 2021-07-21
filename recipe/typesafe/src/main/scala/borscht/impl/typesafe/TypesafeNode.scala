package borscht.impl.typesafe

import borscht.{Meta, Node}
import com.typesafe.config.{ConfigList, ConfigObject, ConfigRenderOptions, ConfigValue}

private[typesafe] trait TypesafeNode(value: ConfigValue) extends Node:
  override lazy val position = TypesafePosition(value.origin)

  override def toString: String = getClass.getSimpleName + "(" + value.render(ConfigRenderOptions.concise) + ")"

private[typesafe] def wrap(value: ConfigValue, meta: Meta): Node = value match
  case list: ConfigList => TypesafeSeqNode(list, meta)
  case obj: ConfigObject => TypesafeCfgNode(obj, meta)
  case scalar => TypesafeScalarNode(scalar, meta)
