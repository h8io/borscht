package borscht.parsers

import borscht.*

import java.util.regex.Pattern
import scala.util.matching.Regex

given NodeParserPattern: NodeParser[Pattern] = node => Pattern.compile(node.as[String])

given NodeParserRegex: PartialNodeParser[Regex] =
  case scalar: ScalarNode => Regex(scalar.asString)
  case cfg: CfgNode => Regex(cfg[String]("pattern"), cfg.list[String]("groups"): _*)
