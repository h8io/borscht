package borscht.template.impl.apache.commons.text.renderers

import borscht.template.impl.apache.commons.text.Placeholder

import java.text.{DateFormat, SimpleDateFormat}
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Locale}

object TemporalRenderer extends Renderer:
  override def apply(ph: Placeholder, value: AnyRef): Option[String] = value match
    case temporal: TemporalAccessor => Some(borscht.util.render(temporal, ph.format, ph.locale))
    case date: Date => Some(render(ph, date))
    case calendar: Calendar => Some(render(ph, calendar.getTime)) 
    case _ => None

  private def render(ph: Placeholder, date: Date): String = (ph.format map { fmt =>
    new SimpleDateFormat(fmt, ph.locale)
  } getOrElse DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, ph.locale)).format(date)
  