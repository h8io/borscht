package borscht.typed

sealed trait ErrorEvent

private[typed] enum Event(val position: Int):
  case TypeName(value: String, override val position: Int) extends Event(position)
  case TypeListStart(override val position: Int) extends Event(position)
  case TypeListEnd(override val position: Int) extends Event(position)
  case TypeListSeparator(override val position: Int) extends Event(position)
  case End(override val position: Int) extends Event(position)
  case InvalidCharacter(char: Char, override val position: Int) extends Event(position) with ErrorEvent
  case None(val previous: Event) extends Event(previous.position)
