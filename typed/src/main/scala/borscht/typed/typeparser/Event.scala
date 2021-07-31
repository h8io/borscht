package borscht.typed.typeparser

private[typeparser] enum Event(val position: Position):
  case TypeName(value: String, override val position: Position) extends Event(position)
  case TypeListStart(override val position: Position) extends Event(position)
  case TypeListEnd(override val position: Position) extends Event(position)
  case TypeListSeparator(override val position: Position) extends Event(position)
  case End(override val position: Position) extends Event(position)
  case InvalidCharacter(char: Char, override val position: Position) extends Event(position)
  case None(val previous: Event) extends Event(previous.position)

  case ValueParser(parser: borscht.typed.ValueParser, override val position: Position) extends Event(position)
  case TypeParameters(parameters: List[borscht.typed.ValueParser], override val position: Position)
    extends Event(position)
