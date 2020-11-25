package borscht.parsers

import java.util.regex.Pattern

import borscht.{Recipe, ObjectNode, NodeParser, ScalarNode}
import borscht.ScalarNode

import scala.util.matching.Regex

given NodeParserPattern(using parser: NodeParser[String]) as NodeParser[Pattern] = parser andThen (Pattern.compile(_))

given NodeParserRegex(using recipe: Recipe) as NodeParser[Regex] =
  ScalarNodeParser andThen { node => Regex(node.asString) } orElse
    (ObjectNodeParser andThen { node  => Regex(node.get[String]("pattern"), node.list[String]("groups"): _*) })
