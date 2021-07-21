package borscht.impl.typesafe

import borscht.{Meta, ScalarNode}
import com.typesafe.config.ConfigValue

private[typesafe] final class TypesafeScalarNode(scalar: ConfigValue,
                                                 val meta: Meta) extends ScalarNode with TypesafeNode(scalar):
  override def withMeta(meta: Meta): ScalarNode = TypesafeScalarNode(scalar, meta)

  override def unwrapped: AnyRef = scalar.unwrapped
