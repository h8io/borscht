package borscht.parsers

import borscht.{ObjectNode, Parser, ScalarNode}

import scala.concurrent.duration.{Duration, FiniteDuration}

given ParserDuration(using parser: Parser[String]) as Parser[Duration]:
  override def apply(node: ScalarNode): Duration = Duration(node.asString)
  override def apply(node: ObjectNode): Duration =
    Duration(node.get[Long]("length"), node.get[String]("unit"))

given ParserFiniteDuration(using parser: Parser[Duration]) as Parser[FiniteDuration] = parser ~> {
  case value: FiniteDuration => value
  case infinite => throw IllegalArgumentException("A finite duration is expected")
}
