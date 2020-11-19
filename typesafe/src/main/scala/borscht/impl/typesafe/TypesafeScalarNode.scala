package borscht.impl.typesafe

import borscht.ScalarNode
import com.typesafe.config.ConfigValue

private[typesafe] final class TypesafeScalarNode private[borscht](scalar: ConfigValue)
  extends ScalarNode with TypesafeNode(scalar):

  override def unwrapped = scalar.unwrapped
