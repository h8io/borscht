package borscht.impl.typesafe

import borscht.{IterableNode, Node, Recipe, ScalarNode}
import com.typesafe.config.ConfigValue

private[typesafe] final class TypesafeScalarNode(scalar: ConfigValue)(using recipe: Recipe)
  extends TypesafeNode(scalar) with ScalarNode with IterableNode with Node:

  override def unwrapped = scalar.unwrapped
