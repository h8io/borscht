package borscht.impl.typesafe

import borscht.{Node, Recipe, ScalarNode}
import com.typesafe.config.ConfigValue

private[typesafe] final class TypesafeScalarNode(scalar: ConfigValue)(using recipe: Recipe)
  extends ScalarNode with TypesafeNode(scalar) with Node:

  override def unwrapped = scalar.unwrapped
