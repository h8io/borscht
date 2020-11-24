package borscht.impl.typesafe

import borscht.Position
import com.typesafe.config.ConfigOrigin

private[typesafe] final class TypesafePosition(origin: ConfigOrigin) extends Position:
  override def toString: String = origin.toString
