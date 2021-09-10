package borscht.typed.types

import borscht.parsers.given
import borscht.typed.RefObj
import borscht.{Node, NodeParser}

import java.text.MessageFormat
import java.util.Locale
import java.util.regex.Pattern
import scala.concurrent.duration.{Duration, FiniteDuration}
import scala.reflect.ClassTag
import scala.util.matching.Regex

trait RefTypeInherited[T <: AnyRef](using parser: NodeParser[T], tag: ClassTag[T]) extends RefTypeParameterless:
  override def apply(node: Node): RefObj[T] = RefObj(parser(node))

object RefTypeBigDecimal extends RefTypeInherited[BigDecimal]

object RefTypeBigInt extends RefTypeInherited[BigInt]

object RefTypeDuration extends RefTypeInherited[Duration]

object RefTypeFiniteDuration extends RefTypeInherited[FiniteDuration]

object RefTypeLocale extends RefTypeInherited[Locale]

object RefTypeMessageFormat extends RefTypeInherited[MessageFormat]

object RefTypePattern extends RefTypeInherited[Pattern]

object RefTypeRegex extends RefTypeInherited[Regex]

object RefTypeString extends RefTypeInherited[String]
