package borscht.parsers

import borscht.{ObjectNode, Parser, ScalarNode}

import scala.concurrent.duration.{Duration, FiniteDuration}

given ParserDuration(using parser: Parser[String]) as Parser[Duration] =
  ScalarNodeParser andThen (node => Duration(node.asString)) orElse
    (ObjectNodeParser andThen { node => Duration(node.get[Long]("length"), node.get[String]("unit")) })

given ParserFiniteDuration(using parser: Parser[Duration]) as Parser[FiniteDuration] = parser andThen {
  case value: FiniteDuration => value
  case infinite => throw IllegalArgumentException("A finite duration is expected")
}
