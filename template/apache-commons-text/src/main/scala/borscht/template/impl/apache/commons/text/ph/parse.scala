package borscht.template.impl.apache.commons.text.ph

import borscht.template.impl.apache.commons.text.ValueFormat

import scala.annotation.tailrec

def parse(chars: String, valueFormat: ValueFormat): (String, ValueFormat) =
  var i = 0

  inline def error(msg: String) = throw PlaceholderParserException(msg, i - 1, chars)

  @tailrec
  def getAttributes(vf: ValueFormat): ValueFormat = getString() match
    case Some("") => error("Attribute name should not be empty")
    case Some(key) =>
      expectEqual()
      getString() match
        case Some(value) => getAttributes(vf(key) = value)
        case _ => error("Unexpected end of the placeholder definition (attribute value expected)") 
    case _ => vf

  def hasNext: Boolean = i < chars.length

  def next(): Char =
    val c = chars(i)
    i += 1
    c

  @tailrec
  def skipWS(): Unit = if hasNext && chars(i).isWhitespace then
    i += 1
    skipWS()

  def expectEqual(): Unit =
    skipWS()
    if (hasNext) {
      if (next() != '=') error("'=' expected")
    } else error("Unexpected end of the placeholder definition ('=' expected)")

  def getString(): Option[String] =
    skipWS()
    if hasNext then Some((next() match
      case '"' => getQuotedString(StringBuilder())
      case c => getBareString(StringBuilder(), c)).result())
    else None

  @tailrec
  def getBareString(sb: StringBuilder, c: Char): StringBuilder = c match
    case ws if ws.isWhitespace => sb
    case '=' =>
      i -= 1
      sb
    case c if isValidBareChar(c) => if (hasNext) getBareString(sb += c, next()) else sb += c
    case c => error(s"Unexpected char '$c' in the bare string")

  def isValidBareChar(c: Char): Boolean = c != '\\' && c != '"'

  @tailrec
  def getQuotedString(sb: StringBuilder): StringBuilder = if hasNext then next() match
    case '"' => sb
    case '\\' => getQuotedString(sb += quotedChar())
    case c => getQuotedString(sb += c)
  else error("Unexpected end of the quoted string ('\"' expected)")

  def quotedChar(): Char =
    if hasNext then next() match
      case '"' => '"'
      case '\\' => '\\'
      case 'n' => '\n'
      case 'r' => '\r'
      case 'b' => '\b'
      case 'f' => '\f'
      case 't' => '\t'
      case 'u' => getCharByCode()
      case _ => error("Illegal quoted char")
    else error("Unexpected end of the quoted character")

  def getCharByCode(): Char =
    @tailrec
    def loop(code: Int, i: Int = 4): Int =
      if (i == 0) code else loop(code * 16 + Character.digit(next(), 16), i - 1)
    loop(0).toChar

  getString() match
    case Some(key) => (key, getAttributes(valueFormat))
    case _ => error("Unexpected end of the placeholder definition (key expected)")
