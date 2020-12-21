package borscht.impl.typesafe

import borscht.ScalarNode
import com.typesafe.config.ConfigValue

private[typesafe] final class TypesafeScalarNode(scalar: ConfigValue) extends ScalarNode with TypesafeNode(scalar):
  override def unwrapped: AnyRef = scalar.unwrapped
