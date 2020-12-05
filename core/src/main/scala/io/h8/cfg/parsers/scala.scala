package io.h8.cfg.parsers

import io.h8.cfg.NodeParser

import scala.concurrent.duration.{Duration, FiniteDuration}

given NodeParserDuration(using parser: NodeParser[String]) as NodeParser[Duration] = parser andThen (Duration(_))

given NodeParserFiniteDuration(using parser: NodeParser[Duration]) as NodeParser[FiniteDuration] = parser andThen {
  case value: FiniteDuration => value
  case infinite => throw IllegalArgumentException("A finite duration is expected")
}
