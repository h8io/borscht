package borscht.typed.types

import borscht.parsers.{NodeParserPattern, NodeParserRegex}

import java.util.regex.Pattern
import scala.util.matching.Regex

object ValueTypePattern extends ValueTypeInherited[Pattern]

object ValueTypeRegex extends ValueTypeInherited[Regex]