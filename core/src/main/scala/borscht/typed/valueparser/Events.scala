package borscht.typed.valueparser

import scala.annotation.tailrec

private[valueparser] final class Events(chars: IndexedSeq[Char]) extends (Parser => Option[Parser]) with Iterator[Event]:
  private var index = 0
  private var _hasNext = true

  override def hasNext: Boolean = _hasNext

  @tailrec
  override def next(): Event =
    import Event.*
    if (index < chars.length) {
      val i = index
      index += 1
      chars(i) match
        case char if char.isWhitespace => next()
        case '[' => TypeListStart(Position(i))
        case ']' => TypeListEnd(Position(i))
        case ',' | ';' => TypeListSeparator(Position(i))
        case char if isTypeNameChar(char) => typeName(StringBuilder() += char, i)
        case char => InvalidCharacter(char, Position(i))
    } else
      _hasNext = false
      End(Position(index))

  private def isTypeNameChar(char: Char) =
    char.isLetterOrDigit || char == '_' || char == '-' || char == '.' || char == '$'

  @tailrec
  private def typeName(builder: StringBuilder, i: Int): Event =
    if (index < chars.length)
      val char = chars(index)
      if (isTypeNameChar(char))
        index += 1
        typeName(builder += char, i)
      else Event.TypeName(builder.result, Position(i))
    else Event.TypeName(builder.result, Position(i))

  override def apply(parser: Parser): Option[Parser] = apply(Some(parser))

  @tailrec
  private def apply(parser: Option[Parser]): Option[Parser] =
    if (hasNext)
      parser match
        case None => None
        case Some(parser) => apply(parser(next))
    else parser
