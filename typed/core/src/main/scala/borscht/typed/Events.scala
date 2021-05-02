package borscht.typed

import scala.annotation.tailrec

private[typed] final class Events(chars: IndexedSeq[Char]) extends Iterator[Event]:
  private var index = 0
  private var _hasNext = true

  override def hasNext: Boolean = _hasNext

  @tailrec
  override def next(): Event =
    import Event._
    if (index < chars.length) {
      val i = index
      index += 1
      chars(i) match
        case char if char.isWhitespace => next()
        case '[' => TypeListStart(i)
        case ']' => TypeListEnd(i)
        case ',' | ';' => TypeListSeparator(i)
        case char if isTypeNameChar(char) => typeName(StringBuilder() += char, i)
        case char => InvalidCharacter(char, i)
    } else
      _hasNext = false
      End(index)

  private def isTypeNameChar(char: Char) =
    char.isLetterOrDigit || char == '_' || char == '-' || char == '.' || char == '$'

  @tailrec
  private def typeName(builder: StringBuilder, i: Int): Event =
    if (index < chars.length)
      val char = chars(index)
      if (isTypeNameChar(char))
        index += 1
        typeName(builder += char, i)
      else Event.TypeName(builder.result, i)
    else Event.TypeName(builder.result, i)
