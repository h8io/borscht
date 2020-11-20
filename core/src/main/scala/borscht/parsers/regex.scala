package borscht.parsers

import java.util.regex.Pattern

import borscht.{Recipe, ObjectNode, Parser, ScalarNode}
import borscht.ScalarNode

import scala.util.matching.Regex

given ParserPattern(using parser: Parser[String]) as Parser[Pattern] = parser ~> Pattern.compile

given ParserRegex(using recipe: Recipe) as Parser[Regex]:
  override def apply(node: ScalarNode): Regex = Regex(node.asString)
  override def apply(node: ObjectNode): Regex =
    Regex(node.get[String]("pattern"), node.list[String]("groups"): _*)
