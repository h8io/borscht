package borscht.parsers

import java.util.regex.Pattern

import borscht.{Recipe, ObjectNode, Parser, ScalarNode}
import borscht.ScalarNode

import scala.util.matching.Regex

given ParserPattern(using parser: Parser[String]) as Parser[Pattern] = parser andThen (Pattern.compile(_))

given ParserRegex(using recipe: Recipe) as Parser[Regex] =
  ScalarNodeParser andThen { node => Regex(node.asString) } orElse
    (ObjectNodeParser andThen { node  => Regex(node.get[String]("pattern"), node.list[String]("groups"): _*) })
