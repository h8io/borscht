package borscht.typed.events

import borscht.typed.Position

sealed trait ErrorEvent

private[typed] enum Event(val position: Position):
  case TypeName(value: String, override val position: Position) extends Event(position)
  case TypeListStart(override val position: Position) extends Event(position)
  case TypeListEnd(override val position: Position) extends Event(position)
  case TypeListSeparator(override val position: Position) extends Event(position)
  case End(override val position: Position) extends Event(position)
  case InvalidCharacter(char: Char, override val position: Position) extends Event(position) with ErrorEvent
  case None(val previous: Event) extends Event(previous.position)
