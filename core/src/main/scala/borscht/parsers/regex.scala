package borscht.parsers

import java.util.regex.Pattern
import borscht.*

import scala.util.matching.Regex

given NodeParserPattern: NodeParser[Pattern] = NodeParserString andThen (Pattern.compile(_))

given NodeParserRegex: NodeParser[Regex] =
  NodeParserPlainString andThen (Regex(_)) orElse
    (NodeParserCfgNode andThen { cfg  => Regex(cfg[String]("pattern"), cfg.list[String]("groups"): _*) })
