package io.h8.cfg.parsers

import java.time._

import borscht.Parser

given ParserZonedDateTime(using parser: Parser[String]) as Parser[ZonedDateTime] =
  parser andThen (ZonedDateTime.parse(_))

given ParserOffsetDateTime(using parser: Parser[String]) as Parser[OffsetDateTime] =
  parser andThen (OffsetDateTime.parse(_))

given ParserOffsetTime(using parser: Parser[String]) as Parser[OffsetTime] =
  parser andThen (OffsetTime.parse(_))

given ParserLocalTime(using parser: Parser[String]) as Parser[LocalTime] =
  parser andThen (LocalTime.parse(_))

given ParserLocalDate(using parser: Parser[String]) as Parser[LocalDate] =
  parser andThen (LocalDate.parse(_))

given ParserYearMonth(using parser: Parser[String]) as Parser[YearMonth] =
  parser andThen (YearMonth.parse(_))

given ParserMonthDay(using parser: Parser[String]) as Parser[MonthDay] =
  parser andThen (MonthDay.parse(_))

given ParserDayOfWeek(using parser: Parser[String]) as Parser[DayOfWeek] =
  parser andThen (_.toUpperCase) andThen DayOfWeek.valueOf

given ParserMonth(using parser: Parser[String]) as Parser[Month] =
  parser andThen (_.toUpperCase) andThen Month.valueOf

given ParserInstant(using parser: Parser[String]) as Parser[Instant] =
  parser andThen (Instant.parse(_))

given ParserJavaDuration(using parser: Parser[String]) as Parser[Duration] =
  parser andThen (Duration.parse(_))

given ParserPeriod(using parser: Parser[String]) as Parser[Period] =
  parser andThen (Period.parse(_))

given ParserZoneId(using parser: Parser[String]) as Parser[ZoneId] =
  parser andThen (ZoneId.of(_))
