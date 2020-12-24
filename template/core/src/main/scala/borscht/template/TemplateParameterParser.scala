package borscht.template

import java.time.format.DateTimeFormatter

type TemplateParameterParser = PartialFunction[String, AnyRef]

object TemplateParameterParser:
  def apply(parsers: Map[String, String => AnyRef] = DefaultParsers,
            separator: String = "::"): TemplateParameterParser = new TemplateParameterParser :
    def apply(value: String): AnyRef = parse(value) match {
      case (Some(parser), raw) => parser(raw)
      case (None, _) => throw IllegalArgumentException(s"Parser not found for $value")
    }

    def isDefinedAt(value: String): Boolean = parse(value)._1.isDefined

    private def parse(value: String): (Option[String => AnyRef], String) = value.split(separator, 2) match
      case Array(v) => None -> v
      case Array(t, v) => parsers.get(t) -> v
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
    "time" -> DateTimeFormatter.ISO_TIME.parse)

  val DefaultParsers: Map[String, String => AnyRef] = NumericParsers ++ DateTimeParsers
