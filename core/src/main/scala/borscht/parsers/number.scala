package borscht.parsers

import borscht.NodeParser

given NodeParserByte: NodeParser[Byte] = NodeParserNumber andThen (_.byteValue)

given NodeParserDouble: NodeParser[Double] = NodeParserNumber andThen (_.doubleValue)

given NodeParserFloat: NodeParser[Float] = NodeParserNumber andThen (_.floatValue)

given NodeParserInt: NodeParser[Int] = NodeParserNumber andThen (_.intValue)

given NodeParserLong: NodeParser[Long] = NodeParserNumber andThen (_.longValue)

given NodeParserShort: NodeParser[Short] = NodeParserNumber andThen (_.shortValue)
