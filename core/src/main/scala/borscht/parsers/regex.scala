package borscht.parsers

import java.util.regex.Pattern

import borscht._

import scala.util.matching.Regex

given NodeParserPattern(using parser: NodeParser[String]) as NodeParser[Pattern] = parser andThen (Pattern.compile(_))

given NodeParserRegex(using recipe: Recipe) as NodeParser[Regex] =
  NodeParserScalarString andThen (Regex(_)) orElse
    (NodeParserObjectNode andThen { node  => Regex(node.get[String]("pattern"), node.list[String]("groups"): _*) })
