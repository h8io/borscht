package borscht.template.impl.apache.commons.text.renderers

import borscht.template.impl.apache.commons.text.ValueFormat

import java.text.{DateFormat, SimpleDateFormat}
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Locale}

object TemporalRenderer extends Renderer:
  override def apply(vf: ValueFormat, value: AnyRef): Option[String] = value match
    case temporal: TemporalAccessor => Some(borscht.util.render(temporal, vf.format, vf.locale))
    case date: Date => Some(render(vf, date))
    case calendar: Calendar => Some(render(vf, calendar.getTime)) 
    case _ => None

  private def render(vf: ValueFormat, date: Date): String = (vf.format map { fmt =>
    new SimpleDateFormat(fmt, vf.locale)
  } getOrElse DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, vf.locale)).format(date)
  