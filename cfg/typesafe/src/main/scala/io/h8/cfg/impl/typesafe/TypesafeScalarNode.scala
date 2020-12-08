package io.h8.cfg.impl.typesafe

import com.typesafe.config.ConfigValue
import io.h8.cfg.ScalarNode

private[typesafe] final class TypesafeScalarNode(scalar: ConfigValue) extends ScalarNode with TypesafeNode(scalar):
  override def unwrapped: AnyRef = scalar.unwrapped
