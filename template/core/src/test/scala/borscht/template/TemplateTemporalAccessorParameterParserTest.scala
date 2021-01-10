package borscht.template

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import java.time.{LocalDate, OffsetDateTime, OffsetTime, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

class TemplateTemporalAccessorParameterParserTest extends AnyFlatSpec with Matchers:
  private val parser = TemplateParameterParser(TemplateParameterParser.DateTimeParsers)

  "DateTime parser" should "return a correct value" in test("datetime:1945-05-07T23:01:00+01:00",
    OffsetDateTime.from, OffsetDateTime.parse("1945-05-07T23:01:00+01:00"))

  "Date parser" should "return a correct value" in test("date:1945-05-07",
    LocalDate.from, LocalDate.parse("1945-05-07"))

  "Time parser" should "return a correct value" in test("time:23:01:00+01:00",
    OffsetTime.from, OffsetTime.parse("23:01:00+01:00"))

  private def test(value: String,
                   cast: TemporalAccessor => TemporalAccessor,
                   expected: TemporalAccessor) = parser(value) match
    case result: TemporalAccessor => cast(result) mustEqual expected
    case result => fail(s"$result is not an instance of TemporalAccessor, but an instance of ${result.getClass}")

  given Ordering[ZonedDateTime] = (zdt1: ZonedDateTime, zdt2: ZonedDateTime) => zdt1.compareTo(zdt2)
  
  "Now parser" should "return a correct value with adjuster" in {
    def time() = ZonedDateTime.now()
      .plusYears(1).plusMonths(2).plusDays(3)
      .plusHours(4).plusMinutes(5).plusSeconds(6)
    
    val before = time()
    parser("now:+1y2m3dT4h5m6s") match
      case result: ZonedDateTime => result must (be >= before and be <= time())
      case result => fail(s"$result is not an instance of ZonedDateTime, but an instance of ${result.getClass}")
  }
