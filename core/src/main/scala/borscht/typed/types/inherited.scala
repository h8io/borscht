package borscht.typed.types

import borscht.parsers.given

import java.lang.Boolean as jBoolean
import java.text.MessageFormat
import java.util.Locale
import java.util.regex.Pattern
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.util.matching.Regex

object RefTypeBoolean extends RefTypeInherited[Boolean]

object RefTypeChar extends RefTypeInherited[Char]

object RefTypeDuration extends RefTypeInherited[Duration]

object RefTypeFiniteDuration extends RefTypeInherited[FiniteDuration]

object RefTypeLocale extends RefTypeInherited[Locale]

object RefTypeMessageFormat extends RefTypeInherited[MessageFormat]

object RefTypePattern extends RefTypeInherited[Pattern]

object RefTypeRegex extends RefTypeInherited[Regex]

object RefTypeString extends RefTypeInherited[String]
