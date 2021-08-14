package borscht.template.renderers

import borscht.CfgNode
import borscht.parsers.NodeParserString

import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

case class TimeFormats(dateFormatter: DateTimeFormatter,
                       timeFormatter: DateTimeFormatter,
                       datetimeFormatter: DateTimeFormatter):
  def this(dateFormat: Option[String],
           timeFormat: Option[String],
           datetimeFormat: Option[String]) = this(
    dateFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE,
    timeFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_TIME,
    datetimeFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE_TIME)

  def this(cfg: CfgNode) = this(cfg.get[String]("date"), cfg.get[String]("time"), cfg.get[String]("datetime"))

  def format(fmt: Option[String], locale: Locale, value: TemporalAccessor): String =
    (fmt map (DateTimeFormatter.ofPattern(_, locale)) getOrElse { value match
      case _: LocalDate => dateFormatter
      case _: (LocalTime | OffsetTime) => timeFormatter
      case _ => datetimeFormatter
    }).format(value)

object TimeFormats:
  object empty extends TimeFormats(None, None, None)
