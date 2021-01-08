package borscht.template.impl.apache.commons.text.renderers

import java.text.{DateFormat, NumberFormat, SimpleDateFormat}
import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.util.{Calendar, Date, Formatter, Locale}

object DefaultRenderer:
  def apply(value: AnyRef, format: Option[String], locale: Locale): String = format map { fmt =>
    val formatter = Formatter(locale)
    try formatter.format(fmt, value).toString finally formatter.close()
  } getOrElse {
    value match
      case n: Number => NumberFormat.getInstance(locale).format(n)
      case s: String => s
      case temporal: TemporalAccessor => borscht.util.render(temporal, None, locale)
      case date: Date => render(date, locale)
      case calendar: Calendar => render(calendar.getTime, locale)
      case _ => value.toString
  }

  private def render(date: Date, locale: Locale): String =
    DateFormat.getDateTimeInstance(DateFormat.FULL, DateFormat.FULL, locale).format(date)
