package borscht.util

private val EncodedCharPattern = raw"\\u([\d+A-Fa-f]{4})".r

def parseChar(value: String): Char =
  if (value.length == 1) value.head else value match
    case EncodedCharPattern(code) => Integer.parseInt(code, 16).toChar
    case _ => throw IllegalArgumentException("Could not parse \"$value\" to a character")