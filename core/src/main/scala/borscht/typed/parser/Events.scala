package borscht.typed.parser

import scala.annotation.tailrec
import scala.collection.{BitSet, mutable}

final private[typed] class Events(chars: IndexedSeq[Char]) extends (Parser => Parser) with Iterator[Event]:
  import Event.*

  private var index = 0
  private var _hasNext = true

  override def hasNext: Boolean = _hasNext

  @tailrec
  override def next(): Event =
    if index < chars.length then
      val i = index
      index += 1
      chars(i) match
        case char if char.isWhitespace => next()
        case '['                       => TypeListStart(Position(i))
        case ']'                       => TypeListEnd(Position(i))
        case ',' | ';'                 => TypeListSeparator(Position(i))
        case char =>
          if isSingleCharName(char) then TypeName(char.toString, Position(i))
          else if isTypeNameChar(char) then typeName(mutable.StringBuilder() += char, i)
          else InvalidCharacter(char, Position(i))
    else
      _hasNext = false
      End(Position(index))

  private def isTypeNameChar(char: Char) = char.isLetterOrDigit || Events.TypeNameSpecialChars.contains(char)

  private def isSingleCharName(char: Char) = Events.SingleCharacterTypeName.contains(char)

  @tailrec
  private def typeName(builder: mutable.StringBuilder, i: Int): Event =
    if index < chars.length then
      val char = chars(index)
      if isTypeNameChar(char) then
        index += 1
        typeName(builder += char, i)
      else TypeName(builder.result, Position(i))
    else TypeName(builder.result, Position(i))

  @tailrec
  override def apply(parser: Parser): Parser =
    if hasNext then apply(parser(next())) else parser

object Events:
  private val TypeNameSpecialChars = BitSet('_', '-', '.')

  private val SingleCharacterTypeName = BitSet('#', '$', '?', '*', '!')
