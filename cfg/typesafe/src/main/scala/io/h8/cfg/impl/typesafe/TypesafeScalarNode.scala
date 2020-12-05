package io.h8.cfg.impl.typesafe

import com.typesafe.config.ConfigValue
import io.h8.cfg.{IterableNode, Node, Factory, ScalarNode}

private[typesafe] final class TypesafeScalarNode(scalar: ConfigValue)(using factory: Factory)
  extends ScalarNode with IterableNode with Node with TypesafeNode(scalar):

  override def unwrapped: AnyRef = scalar.unwrapped
