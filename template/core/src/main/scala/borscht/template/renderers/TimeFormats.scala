package borscht.template.renderers

import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

case class TimeFormats(dateFormatter: DateTimeFormatter,
                       timeFormatter: DateTimeFormatter,
                       datetimeFormatter: DateTimeFormatter):
  def this(date: Option[String], time: Option[String], datetime: Option[String]) = this(
    date map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE,
    time map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_TIME,
    datetime map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE_TIME)

  def this(formats: Map[String, String]) = this(formats.get("date"), formats.get("time"), formats.get("datetime"))

  def format(fmt: Option[String], locale: Locale, value: TemporalAccessor): String =
    (fmt map (DateTimeFormatter.ofPattern(_, locale)) getOrElse { value match
      case _: LocalDate => dateFormatter
      case _: (LocalTime | OffsetTime) => timeFormatter
      case _ => datetimeFormatter
    }).format(value)

object TimeFormats:
  object default extends TimeFormats(None, None, None)
