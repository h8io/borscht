package borscht.template.impl.apache.commons.text.renderers

import borscht.CfgNode
import borscht.parsers.{NodeParserCfgNode, NodeParserString}
import borscht.template.impl.apache.commons.text.ValueFormat

import java.text.{DateFormat, SimpleDateFormat}
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Locale}

final class TemporalRenderer(formats: TemporalRenderer.Formats) extends Renderer:
  def this() = this(TemporalRenderer.DefaultFormats)

  def this(cfg: CfgNode) = this(cfg.get[CfgNode]("formats") map { formats =>
    new TemporalRenderer.Formats(
      formats.get[String]("date"),
      formats.get[String]("time"),
      formats.get[String]("datetime"))
  } getOrElse TemporalRenderer.DefaultFormats)

  override def apply(vf: ValueFormat, value: AnyRef): Option[String] = value match
    case temporal: TemporalAccessor =>
      Some((vf.format map DateTimeFormatter.ofPattern getOrElse formats.formatter(temporal)).format(temporal))
    case date: Date => Some(render(vf, date))
    case calendar: Calendar => Some(render(vf, calendar.getTime)) 
    case _ => None

  private def render(vf: ValueFormat, date: Date): String = (vf.format map { fmt =>
    new SimpleDateFormat(fmt, vf.locale)
  } getOrElse DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, vf.locale)).format(date)

object TemporalRenderer:
  case class Formats(dateFormatter: DateTimeFormatter,
                     timeFormatter: DateTimeFormatter,
                     datetimeFormatter: DateTimeFormatter):
    def this(dateFormat: Option[String],
             timeFormat: Option[String],
             datetimeFormat: Option[String]) = this(
      dateFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE,
      timeFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_TIME,
      datetimeFormat map DateTimeFormatter.ofPattern getOrElse DateTimeFormatter.ISO_DATE_TIME)

    def formatter(value: TemporalAccessor): DateTimeFormatter = value match
      case _: LocalDate => dateFormatter
      case _: (LocalTime | OffsetTime) => timeFormatter
      case _ => datetimeFormatter

  object DefaultFormats extends Formats(None, None, None)