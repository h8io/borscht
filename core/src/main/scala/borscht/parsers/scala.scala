package borscht.parsers

import borscht.NodeParser

import scala.concurrent.duration.{Duration, FiniteDuration}

given NodeParserDuration: NodeParser[Duration] = NodeParserString andThen (Duration(_))

given NodeParserFiniteDuration: NodeParser[FiniteDuration] = NodeParserDuration andThen {
  case value: FiniteDuration => value
  case infinite => throw IllegalArgumentException("A finite duration is expected")
}
