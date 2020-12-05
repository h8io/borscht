package io.h8.cfg.parsers

import java.time._

import io.h8.cfg.NodeParser

given NodeParserZonedDateTime(using parser: NodeParser[String]) as NodeParser[ZonedDateTime] =
  parser andThen (ZonedDateTime.parse(_))

given NodeParserOffsetDateTime(using parser: NodeParser[String]) as NodeParser[OffsetDateTime] =
  parser andThen (OffsetDateTime.parse(_))

given NodeParserOffsetTime(using parser: NodeParser[String]) as NodeParser[OffsetTime] =
  parser andThen (OffsetTime.parse(_))

given NodeParserLocalTime(using parser: NodeParser[String]) as NodeParser[LocalTime] =
  parser andThen (LocalTime.parse(_))

given NodeParserLocalDate(using parser: NodeParser[String]) as NodeParser[LocalDate] =
  parser andThen (LocalDate.parse(_))

given NodeParserYearMonth(using parser: NodeParser[String]) as NodeParser[YearMonth] =
  parser andThen (YearMonth.parse(_))

given NodeParserMonthDay(using parser: NodeParser[String]) as NodeParser[MonthDay] =
  parser andThen (MonthDay.parse(_))

given NodeParserDayOfWeek(using parser: NodeParser[String]) as NodeParser[DayOfWeek] =
  parser andThen (_.toUpperCase) andThen DayOfWeek.valueOf

given NodeParserMonth(using parser: NodeParser[String]) as NodeParser[Month] =
  parser andThen (_.toUpperCase) andThen Month.valueOf

given NodeParserInstant(using parser: NodeParser[String]) as NodeParser[Instant] =
  parser andThen (Instant.parse(_))

given NodeParserJavaDuration(using parser: NodeParser[String]) as NodeParser[Duration] =
  parser andThen (Duration.parse(_))

given NodeParserPeriod(using parser: NodeParser[String]) as NodeParser[Period] =
  parser andThen (Period.parse(_))

given NodeParserZoneId(using parser: NodeParser[String]) as NodeParser[ZoneId] =
  parser andThen (ZoneId.of(_))
