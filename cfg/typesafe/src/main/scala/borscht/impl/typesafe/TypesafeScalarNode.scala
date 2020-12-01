package borscht.impl.typesafe

import borscht.{IterableNode, Node, Recipe, ScalarNode}
import com.typesafe.config.ConfigValue

private[typesafe] final class TypesafeScalarNode(scalar: ConfigValue)(using recipe: Recipe)
  extends ScalarNode with IterableNode with Node with TypesafeNode(scalar):

  override def unwrapped: AnyRef = scalar.unwrapped
