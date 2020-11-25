package borscht.parsers

import borscht.NodeParser

given NodeParserByte(using parser: NodeParser[Number]) as NodeParser[Byte] = parser andThen (_.byteValue)

given NodeParserDouble(using parser: NodeParser[Number]) as NodeParser[Double] = parser andThen (_.doubleValue)

given NodeParserFloat(using parser: NodeParser[Number]) as NodeParser[Float] = parser andThen (_.floatValue)

given NodeParserInt(using parser: NodeParser[Number]) as NodeParser[Int] = parser andThen (_.intValue)

given NodeParserLong(using parser: NodeParser[Number]) as NodeParser[Long] = parser andThen (_.longValue)

given NodeParserShort(using parser: NodeParser[Number]) as NodeParser[Short] = parser andThen (_.shortValue)
