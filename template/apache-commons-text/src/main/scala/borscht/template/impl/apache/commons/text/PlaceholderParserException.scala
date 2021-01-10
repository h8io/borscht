package borscht.template.impl.apache.commons.text

import scala.annotation.tailrec

private def pointer(value: String, position: Int) =
  val chars = value.iterator
  val sb = StringBuilder()
  @tailrec
  def append(i: Int): StringBuilder =
    if i > 0 && chars.hasNext then
      val c = chars.next
      if (c.isWhitespace) sb += c else sb += ' '
      append(i - 1)
    else sb += '^'
  append(position)

class PlaceholderParserException(message: String, position: Int, value: String)
  extends RuntimeException(s"$message @ $position\n$value\n${pointer(value, position)}")
