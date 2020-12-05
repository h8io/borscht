package io.h8.cfg.impl.typesafe

import com.typesafe.config.ConfigOrigin
import io.h8.cfg.Position

private[typesafe] final class TypesafePosition(origin: ConfigOrigin) extends Position:
  override def toString: String = s"typesafe(${origin.lineNumber}@${Option(origin.filename) getOrElse "-"})"
