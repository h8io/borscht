package borscht.typed.types

import borscht.parsers.{NodeParserPattern, NodeParserRegex}

import java.util.regex.Pattern
import scala.util.matching.Regex

object ValueTypePattern extends RefTypeInherited[Pattern]

object ValueTypeRegex extends RefTypeInherited[Regex]