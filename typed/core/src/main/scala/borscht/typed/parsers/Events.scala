package borscht.typed.parsers

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
        case ',' => TypeListSeparator(i)
        case char if isTypeNameStart(char) => typeName(StringBuilder() += char, i)
        case char => Error(s"unexpected character $char (${char.toInt.toHexString})", i)
    } else
      _hasNext = false
      End(index)

  private def isTypeNameStart(char: Char) = char.isLetter || char == '_'

  private def isTypeNameChar(char: Char) = char.isLetterOrDigit || char == '_'

  @tailrec
  private def typeName(builder: StringBuilder, i: Int): Event =
    if (index < chars.length)
      val char = chars(index)
      if (isTypeNameChar(char))
        index += 1
        typeName(builder += char, i)
      else Event.TypeName(builder.result, i)
    else Event.TypeName(builder.result, i)
