package io.h8.cfg.parsers

import java.time._

import io.h8.cfg.NodeParser

//import scala.language.unsafeNulls

given NodeParserZonedDateTime(using parser: NodeParser[String]): NodeParser[ZonedDateTime] =
  parser andThen (ZonedDateTime.parse(_))

given NodeParserOffsetDateTime(using parser: NodeParser[String]): NodeParser[OffsetDateTime] =
  parser andThen (OffsetDateTime.parse(_))

given NodeParserOffsetTime(using parser: NodeParser[String]): NodeParser[OffsetTime] =
  parser andThen (OffsetTime.parse(_))

given NodeParserLocalTime(using parser: NodeParser[String]): NodeParser[LocalTime] =
  parser andThen (LocalTime.parse(_))

given NodeParserLocalDate(using parser: NodeParser[String]): NodeParser[LocalDate] =
  parser andThen (LocalDate.parse(_))

given NodeParserYearMonth(using parser: NodeParser[String]): NodeParser[YearMonth] =
  parser andThen (YearMonth.parse(_))

given NodeParserMonthDay(using parser: NodeParser[String]): NodeParser[MonthDay] =
  parser andThen (MonthDay.parse(_))

given NodeParserDayOfWeek(using parser: NodeParser[String]): NodeParser[DayOfWeek] =
  parser andThen (_.toUpperCase) andThen DayOfWeek.valueOf

given NodeParserMonth(using parser: NodeParser[String]): NodeParser[Month] =
  parser andThen (_.toUpperCase) andThen Month.valueOf

given NodeParserInstant(using parser: NodeParser[String]): NodeParser[Instant] =
  parser andThen (Instant.parse(_))

given NodeParserJavaDuration(using parser: NodeParser[String]): NodeParser[Duration] =
  parser andThen (Duration.parse(_))

given NodeParserPeriod(using parser: NodeParser[String]): NodeParser[Period] =
  parser andThen (Period.parse(_))

given NodeParserZoneId(using parser: NodeParser[String]): NodeParser[ZoneId] =
  parser andThen (ZoneId.of(_))
