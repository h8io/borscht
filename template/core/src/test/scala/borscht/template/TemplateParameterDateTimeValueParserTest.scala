package borscht.template

import java.time.{LocalDate, OffsetTime, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TemplateParameterDateTimeValueParserTest extends AnyFlatSpec with Matchers:
  private val parser = TemplateParameterValueParser(
    parsers = TemplateParameterValueParser.DateTimeParsers,
    separator = "//")

  "Template parameter value parser" should "parse a date and time value with time zone" in {
    val result = parser("datetime//2011-12-03T10:15:30+01:00[Europe/Paris]")
    result should be (a[TemporalAccessor])
    ZonedDateTime.from(result.asInstanceOf[TemporalAccessor]) shouldEqual
      ZonedDateTime.parse("2011-12-03T10:15:30+01:00[Europe/Paris]")
  }

  it should "parse a date value" in {
    val result = parser("date//2011-12-03")
    result should be (a[TemporalAccessor])
    LocalDate.from(result.asInstanceOf[TemporalAccessor]) shouldEqual LocalDate.parse("2011-12-03")
  }

  it should "parse a time value" in {
    val result = parser("time//10:15:30+01:00")
    result should be (a[TemporalAccessor])
    OffsetTime.from(result.asInstanceOf[TemporalAccessor]) shouldEqual OffsetTime.parse("10:15:30+01:00")
  }
