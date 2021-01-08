package borscht.template.impl.apache.commons.text.renderers

import java.text.{DateFormat, SimpleDateFormat}
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Locale}

object TemporalRenderer extends Renderer:
  override def apply(value: AnyRef, format: Option[String], locale: Locale): Option[String] = value match
    case temporal: TemporalAccessor => Some(borscht.util.render(temporal, format, locale))
    case date: Date => Some(render(date, format, locale))
    case calendar: Calendar => Some(render(calendar.getTime, format, locale)) 
    case _ => None

  private def render(date: Date, format: Option[String], locale: Locale): String = (format map { fmt =>
    new SimpleDateFormat(fmt, locale)
  } getOrElse DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale)).format(date)
  