package io.h8.cfg.parsers

import java.time._

import borscht.Parser

given ParserZonedDateTime(using parser: Parser[String]) as Parser[ZonedDateTime] = parser ~> ZonedDateTime.parse

given ParserOffsetDateTime(using parser: Parser[String]) as Parser[OffsetDateTime] = parser ~> OffsetDateTime.parse

given ParserOffsetTime(using parser: Parser[String]) as Parser[OffsetTime] = parser ~> OffsetTime.parse

given ParserLocalTime(using parser: Parser[String]) as Parser[LocalTime] = parser ~> LocalTime.parse

given ParserLocalDate(using parser: Parser[String]) as Parser[LocalDate] = parser ~> LocalDate.parse

given ParserYearMonth(using parser: Parser[String]) as Parser[YearMonth] = parser ~> YearMonth.parse

given ParserMonthDay(using parser: Parser[String]) as Parser[MonthDay] = parser ~> MonthDay.parse

given ParserDayOfWeek(using parser: Parser[String]) as Parser[DayOfWeek] =
  parser ~> (_.toUpperCase) ~> DayOfWeek.valueOf

given ParserMonth(using parser: Parser[String]) as Parser[Month] = parser ~> (_.toUpperCase) ~> Month.valueOf

given ParserInstant(using parser: Parser[String]) as Parser[Instant] = parser ~> Instant.parse

given ParserJavaDuration(using parser: Parser[String]) as Parser[Duration] = parser ~> Duration.parse

given ParserPeriod(using parser: Parser[String]) as Parser[Period] = parser ~> Period.parse

given ParserZoneId(using parser: Parser[String]) as Parser[ZoneId] = parser ~> ZoneId.of
