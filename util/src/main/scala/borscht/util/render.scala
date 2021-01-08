package borscht.util

import java.time.{LocalDate, LocalTime, OffsetTime}
import java.time.format.{DateTimeFormatter, FormatStyle}
import java.time.temporal.TemporalAccessor
import java.util.Locale

def render(value: TemporalAccessor, format: Option[String], locale: Locale): String =
  (format map (DateTimeFormatter.ofPattern(_, locale)) getOrElse (value match
    case _: LocalDate => DateTimeFormatter.ISO_DATE
    case _: (LocalTime | OffsetTime) => DateTimeFormatter.ISO_TIME
    case _ => DateTimeFormatter.ISO_DATE_TIME)).format(value)
