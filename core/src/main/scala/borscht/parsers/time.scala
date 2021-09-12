package borscht.parsers

import borscht.NodeParser
import borscht.time.DateTimeAdjuster

import java.time.*
import java.time.format.DateTimeFormatter

given NodeParserDateTimeFormatter: NodeParser[DateTimeFormatter] =
  NodeParserString andThen DateTimeFormatter.ofPattern

given NodeParserZonedDateTime: NodeParser[ZonedDateTime] =
  NodeParserString andThen (ZonedDateTime.parse(_))

given NodeParserOffsetDateTime: NodeParser[OffsetDateTime] =
  NodeParserString andThen (OffsetDateTime.parse(_))

given NodeParserOffsetTime: NodeParser[OffsetTime] =
  NodeParserString andThen (OffsetTime.parse(_))

given NodeParserLocalTime: NodeParser[LocalTime] =
  NodeParserString andThen (LocalTime.parse(_))

given NodeParserLocalDate: NodeParser[LocalDate] =
  NodeParserString andThen (LocalDate.parse(_))

given NodeParserYearMonth: NodeParser[YearMonth] =
  NodeParserString andThen (YearMonth.parse(_))

given NodeParserYear: NodeParser[Year] =
  NodeParserString andThen (_.toUpperCase) andThen (Year.parse(_))

given NodeParserMonthDay: NodeParser[MonthDay] =
  NodeParserString andThen (MonthDay.parse(_))

given NodeParserDayOfWeek: NodeParser[DayOfWeek] =
  NodeParserString andThen (_.toUpperCase) andThen DayOfWeek.valueOf

given NodeParserMonth: NodeParser[Month] =
  NodeParserString andThen (_.toUpperCase) andThen Month.valueOf

given NodeParserInstant: NodeParser[Instant] =
  NodeParserString andThen Instant.parse

given NodeParserJavaDuration: NodeParser[Duration] =
  NodeParserString andThen (Duration.parse(_))

given NodeParserPeriod: NodeParser[Period] =
  NodeParserString andThen (Period.parse(_))

given NodeParserZoneId: NodeParser[ZoneId] =
  NodeParserString andThen (ZoneId.of(_))

given NodeParserDateTimeAdjuster: NodeParser[DateTimeAdjuster] =
  NodeParserString andThen DateTimeAdjuster
