package borscht.typed.parsers

private[parsers] enum Event(val position: Int):
  case TypeName(value: String, override val position: Int) extends Event(position)
  case TypeListStart(override val position: Int) extends Event(position)
  case TypeListEnd(override val position: Int) extends Event(position)
  case TypeListSeparator(override val position: Int) extends Event(position)
  case End(override val position: Int) extends Event(position)
  case Error(message: String, override val position: Int) extends Event(position)
  case None(previous: Event) extends Event(previous.position)
