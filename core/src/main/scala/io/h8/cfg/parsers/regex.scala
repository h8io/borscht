package io.h8.cfg.parsers

import java.util.regex.Pattern
import io.h8.cfg._

import scala.util.matching.Regex

given NodeParserPattern(using parser: NodeParser[String]) as NodeParser[Pattern] = parser andThen (Pattern.compile(_))

given NodeParserRegex(using parser: NodeParser[String]) as NodeParser[Regex] =
  NodeParserPlainString andThen (Regex(_)) orElse
    (NodeParserConfigNode andThen { cfg  => Regex(cfg[String]("pattern"), cfg.list[String]("groups"): _*) })
