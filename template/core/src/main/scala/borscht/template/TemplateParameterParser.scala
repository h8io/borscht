package borscht.template

import borscht.time.DateTimeAdjuster

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

type TemplateParameterParser = PartialFunction[String, AnyRef]

object TemplateParameterParser:
  def apply(parsers: Map[String, String => AnyRef] = DefaultParsers): TemplateParameterParser =
    new TemplateParameterParser:
      def apply(value: String): AnyRef = parse(value) match
        case (Some(parser), raw) => parser(raw)
        case (None, _) => throw IllegalArgumentException(s"Parser not found for $value")

      def isDefinedAt(value: String): Boolean = parse(value)._1.isDefined

      private def parse(value: String): (Option[String => _ <: AnyRef], String) = value.split(":", 2) match
        case Array(v) => Some(identity[String]) -> v
        case Array(t, v) => (t.split('>') map parsers.get reduce {
          case (Some(p1), Some(p2)) => Some((w: String) => p2(p1(w).toString))
          case _ => None
        }) -> v
        case _ => throw IllegalStateException("It should not happen")

  val NumericParsers: Map[String, String => AnyRef] = Map(
    "bigint" -> BigInt.apply,
    "bigdecimal" -> BigDecimal.apply,
    "byte" -> java.lang.Byte.valueOf,
    "double" -> java.lang.Double.valueOf,
    "float" -> java.lang.Float.valueOf,
    "int" -> Integer.valueOf,
    "long" -> java.lang.Long.valueOf,
    "short" -> java.lang.Short.valueOf)

  val DateTimeParsers: Map[String, String => AnyRef] = Map(
    "datetime" -> DateTimeFormatter.ISO_DATE_TIME.parse,
    "date" -> DateTimeFormatter.ISO_DATE.parse,
    "time" -> DateTimeFormatter.ISO_TIME.parse,
    "now" -> { (adjuster: String) => ZonedDateTime.now().`with`(DateTimeAdjuster(adjuster)) })

  val MiscParsers: Map[String, String => AnyRef] = Map(
    "env" -> sys.env,
    "prop" -> sys.props)

  val DefaultParsers: Map[String, String => AnyRef] = NumericParsers ++ DateTimeParsers ++ MiscParsers
