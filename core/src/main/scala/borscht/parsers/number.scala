package borscht.parsers

import borscht.Parser

given ParserByte(using parser: Parser[Number]) as Parser[Byte] = parser ~> (_.byteValue)

given ParserDouble(using parser: Parser[Number]) as Parser[Double] = parser ~> (_.doubleValue)

given ParserFloat(using parser: Parser[Number]) as Parser[Float] = parser ~> (_.floatValue)

given ParserInt(using parser: Parser[Number]) as Parser[Int] = parser ~> (_.intValue)

given ParserLong(using parser: Parser[Number]) as Parser[Long] = parser ~> (_.longValue)

given ParserShort(using parser: Parser[Number]) as Parser[Short] = parser ~> (_.shortValue)
