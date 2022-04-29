package borscht.impl.typesafe

import borscht.{Meta, ScalarNode}
import com.typesafe.config.ConfigValue

final private[typesafe] class TypesafeScalarNode(scalar: ConfigValue, val meta: Meta)
    extends ScalarNode
    with TypesafeNode(scalar):
  override def withMeta(meta: Meta): ScalarNode = TypesafeScalarNode(scalar, meta)

  override def value: AnyRef = scalar.unwrapped
