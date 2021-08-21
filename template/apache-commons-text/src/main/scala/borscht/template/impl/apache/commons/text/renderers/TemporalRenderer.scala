package borscht.template.impl.apache.commons.text.renderers

import borscht.CfgNode
import borscht.parsers.{NodeParserCfgNode, NodeParserString}
import borscht.template.impl.apache.commons.text.ValueFormat
import borscht.template.renderers.TimeFormats

import java.text.{DateFormat, SimpleDateFormat}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.TemporalAccessor
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.util.{Calendar, Date, Locale}

final class TemporalRenderer(formats: TimeFormats) extends Renderer:
  def this() = this(TimeFormats.default)

  override def apply(vf: ValueFormat, value: Any): Option[String] = value match
    case temporal: TemporalAccessor => Some(formats.format(vf.format, vf.locale, temporal))
    case date: Date => Some(render(vf, date))
    case calendar: Calendar => Some(render(vf, calendar.getTime)) 
    case _ => None

  private def render(vf: ValueFormat, date: Date): String = (vf.format map { fmt =>
    new SimpleDateFormat(fmt, vf.locale)
  } getOrElse DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, vf.locale)).format(date)
