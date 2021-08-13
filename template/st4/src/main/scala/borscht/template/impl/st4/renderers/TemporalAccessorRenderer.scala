package borscht.template.impl.st4.renderers

import borscht.CfgNode
import borscht.parsers.{NodeParserCfgNode, NodeParserString}

import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.Locale

final class TemporalAccessorRenderer(formats: TemporalAccessorRenderer.Formats) extends Renderer[TemporalAccessor]:
  def this() = this(TemporalAccessorRenderer.DefaultFormats)

  def this(cfg: CfgNode) = this(cfg.get[CfgNode]("formats") map { formats =>
    new TemporalAccessorRenderer.Formats(
      formats.get[String]("date"),
      formats.get[String]("time"),
      formats.get[String]("datetime"))
  } getOrElse TemporalAccessorRenderer.DefaultFormats)

  override def toString(value: TemporalAccessor, format: (String | Null), locale: Locale): String =
    (Option(format) map (DateTimeFormatter.ofPattern(_, locale)) getOrElse {
      value match
        case _: LocalDate => DateTimeFormatter.ISO_DATE
        case _: (LocalTime | OffsetTime) => DateTimeFormatter.ISO_TIME
        case _ => DateTimeFormatter.ISO_DATE_TIME
    }).format(value)

object TemporalAccessorRenderer:
  case class Formats(dateFormatter: DateTimeFormatter,
                     timeFormatter: DateTimeFormatter,
                     datetimeFormatter: DateTimeFormatter):
    def this(dateFormat: Option[String],
             timeFormat: Option[String],
             datetimeFormat: Option[String]) = this(
      dateFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE,
      timeFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_TIME,
      datetimeFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE_TIME)

  object DefaultFormats extends Formats(None, None, None)