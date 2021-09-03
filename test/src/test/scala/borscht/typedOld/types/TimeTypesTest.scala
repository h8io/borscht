package borscht.typedOld.types

import borscht.test.scalar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.{ZoneId, ZoneOffset, ZonedDateTime}

class TimeTypesTest extends AnyFlatSpec with Matchers:
  "Zone value type" should "parse a region id" in {
    ValueTypeZoneId.parser(Nil) map (_(scalar("Asia/Tokyo"))) shouldEqual Right(ZoneId.of("Asia/Tokyo"))
  }

  it should "parse an offset for a prefix" in {
    ValueTypeZoneId.parser(Nil) map (_(scalar("GMT+15:30"))) shouldEqual Right(ZoneId.of("GMT+15:30"))
  }

  "Zone offset type" should "parse Z" in {
    ValueTypeZoneOffset.parser(Nil) map (_(scalar("Z"))) shouldEqual Right(ZoneOffset.UTC)
  }

  it should "parse zero" in {
    ValueTypeZoneOffset.parser(Nil) map (_(scalar("+0"))) shouldEqual Right(ZoneOffset.UTC)
  }

  it should "parse an hours value" in {
    ValueTypeZoneOffset.parser(Nil) map (_(scalar("+9"))) shouldEqual Right(ZoneOffset.ofHours(9))
  }

  it should "parse an hours and minutes value" in {
    ValueTypeZoneOffset.parser(Nil) map (_(scalar("-08:30"))) shouldEqual
      Right(ZoneOffset.ofHoursMinutes(-8, -30))
  }

  "Now" should "be parsed without adjuster and time zone" in {
    val before = ZonedDateTime.now
    val now = ValueTypeNow().parser(Nil) map (_(scalar("")))
    val after = ZonedDateTime.now
    now match
      case Right(value: ZonedDateTime) => value should (be >= before and be <= after)
      case _ => fail("Unexpected result $now")
  }

  it should "be parsed with adjuster" in {
    val before = ZonedDateTime.now.minusDays(1)
    val now = ValueTypeNow().parser(Nil) map (_(scalar("-1d")))
    val after = ZonedDateTime.now.minusDays(1)
    now match
      case Right(value: ZonedDateTime) => value should (be >= before and be <= after)
      case _ => fail("Unexpected result $now")
  }

  it should "be parsed with adjuster and time zone" in {
    val zone = "Europe/London"
    val before = ZonedDateTime.now.minusMonths(1)
    val now = ValueTypeNow(zone).parser(Nil) map (_(scalar("-0000-01-00")))
    val after = ZonedDateTime.now.minusMonths(1)
    now match
      case Right(value: ZonedDateTime) =>
        value.getZone shouldBe ZoneId.of(zone)
        value should (be >= before and be <= after)
      case _ => fail("Unexpected result $now")
  }
