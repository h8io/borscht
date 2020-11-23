package borscht.parsers

import borscht.Parser

given ParserByte(using parser: Parser[Number]) as Parser[Byte] = parser andThen (_.byteValue)

given ParserDouble(using parser: Parser[Number]) as Parser[Double] = parser andThen (_.doubleValue)

given ParserFloat(using parser: Parser[Number]) as Parser[Float] = parser andThen (_.floatValue)

given ParserInt(using parser: Parser[Number]) as Parser[Int] = parser andThen (_.intValue)

given ParserLong(using parser: Parser[Number]) as Parser[Long] = parser andThen (_.longValue)

given ParserShort(using parser: Parser[Number]) as Parser[Short] = parser andThen (_.shortValue)
