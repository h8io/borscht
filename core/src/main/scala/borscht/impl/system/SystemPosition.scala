package borscht.impl.system

import borscht.Position

private[system] object SystemPosition extends Position.Some:
  override def toString: String = "system"
