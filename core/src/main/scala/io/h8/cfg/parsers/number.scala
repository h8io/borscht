package io.h8.cfg.parsers

import io.h8.cfg.NodeParser

given NodeParserByte as NodeParser[Byte] = NodeParserNumber andThen (_.byteValue)

given NodeParserDouble as NodeParser[Double] = NodeParserNumber andThen (_.doubleValue)

given NodeParserFloat as NodeParser[Float] = NodeParserNumber andThen (_.floatValue)

given NodeParserInt as NodeParser[Int] = NodeParserNumber andThen (_.intValue)

given NodeParserLong as NodeParser[Long] = NodeParserNumber andThen (_.longValue)

given NodeParserShort as NodeParser[Short] = NodeParserNumber andThen (_.shortValue)
