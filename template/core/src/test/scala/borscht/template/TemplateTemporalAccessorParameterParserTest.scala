package borscht.template

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import java.time.{LocalDate, OffsetDateTime, OffsetTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

class TemplateTemporalAccessorParameterParserTest extends AnyFlatSpec with Matchers:
  private val parser = TemplateParameterParser(TemplateParameterParser.DateTimeParsers)

  "DateTime parser" should "return a correct value" in test("datetime::1945-05-07T23:01:00+01:00",
    OffsetDateTime.from, OffsetDateTime.parse("1945-05-07T23:01:00+01:00"))

  "Date parser" should "return a correct value" in test("date::1945-05-07",
    LocalDate.from, LocalDate.parse("1945-05-07"))

  "Time parser" should "return a correct value" in test("time::23:01:00+01:00",
    OffsetTime.from, OffsetTime.parse("23:01:00+01:00"))

  private def test(value: String,
                   cast: TemporalAccessor => TemporalAccessor,
                   expected: TemporalAccessor) = parser(value) match
    case result: TemporalAccessor => cast(result) mustEqual expected
    case result => fail(s"$result was not an instance of TemporalAccessor, but an instance of  ${result.getClass}")
