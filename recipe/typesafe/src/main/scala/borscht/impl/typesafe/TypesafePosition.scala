package borscht.impl.typesafe

import borscht.Position
import com.typesafe.config.ConfigOrigin

final private[typesafe] class TypesafePosition(origin: ConfigOrigin) extends Position.Some:
  override def toString: String = s"typesafe(${origin.lineNumber}@${Option(origin.filename) getOrElse "-"})"
