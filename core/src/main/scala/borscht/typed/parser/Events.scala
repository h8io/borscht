package borscht.typed.parser

import borscht.typed.parser.Events.{SingleCharacterTypeName, TypeNameSpecialChars}

import scala.annotation.tailrec
import scala.collection.BitSet

private[typed] final class Events(chars: IndexedSeq[Char]) extends (Parser => Parser) with Iterator[Event]:
  import Event.*

  private var index = 0
  private var _hasNext = true

  override def hasNext: Boolean = _hasNext

  @tailrec
  override def next(): Event =
    if (index < chars.length)
      val i = index
      index += 1
      chars(i) match
        case char if char.isWhitespace => next()
        case '[' => TypeListStart(Position(i))
        case ']' => TypeListEnd(Position(i))
        case ',' | ';' => TypeListSeparator(Position(i))
        case char =>
          if (isSingleCharName(char)) TypeName(char.toString, Position(i))
          else if (isTypeNameChar(char)) typeName(StringBuilder() += char, i)
          else InvalidCharacter(char, Position(i))
    else
      _hasNext = false
      End(Position(index))
  
  private def isTypeNameChar(char: Char) = char.isLetterOrDigit || TypeNameSpecialChars.contains(char)

  private def isSingleCharName(char: Char) = SingleCharacterTypeName.contains(char)

  @tailrec
  private def typeName(builder: StringBuilder, i: Int): Event =
    if (index < chars.length)
      val char = chars(index)
      if (isTypeNameChar(char))
        index += 1
        typeName(builder += char, i)
      else TypeName(builder.result, Position(i))
    else TypeName(builder.result, Position(i))

  @tailrec
  override def apply(parser: Parser): Parser =
    if (hasNext) apply(parser(next)) else parser

object Events:
  private val TypeNameSpecialChars = BitSet('_', '-', '.')

  private val SingleCharacterTypeName = BitSet('#', '$', '?', '*')
