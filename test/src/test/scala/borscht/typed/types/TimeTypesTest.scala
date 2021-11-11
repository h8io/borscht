package borscht.typed.types

import borscht.test.scalar
import borscht.typed.Ref
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.{ZoneId, ZoneOffset, ZonedDateTime}

class TimeTypesTest extends AnyFlatSpec with Matchers:
  "Zone value type" should "parse a region id" in {
    RefTypeZoneId.parser(Nil) map (_(scalar("Asia/Tokyo"))) shouldEqual
      Right(Ref(ZoneId.of("Asia/Tokyo")))
  }

  it should "parse an offset for a prefix" in {
    RefTypeZoneId.parser(Nil) map (_(scalar("GMT+15:30"))) shouldEqual
      Right(Ref(ZoneId.of("GMT+15:30")))
  }

  "Zone offset type" should "parse Z" in {
    RefTypeZoneOffset.parser(Nil) map (_(scalar("Z"))) shouldEqual Right(Ref(ZoneOffset.UTC))
  }

  it should "parse zero" in {
    RefTypeZoneOffset.parser(Nil) map (_(scalar("+0"))) shouldEqual Right(Ref(ZoneOffset.UTC))
  }

  it should "parse an hours value" in {
    RefTypeZoneOffset.parser(Nil) map (_(scalar("+9"))) shouldEqual Right(Ref(ZoneOffset.ofHours(9)))
  }

  it should "parse an hours and minutes value" in {
    RefTypeZoneOffset.parser(Nil) map (_(scalar("-08:30"))) shouldEqual
      Right(Ref(ZoneOffset.ofHoursMinutes(-8, -30)))
  }

  "Now" should "be parsed without adjuster and time zone" in {
    val before = ZonedDateTime.now
    val now = RefTypeNow().parser(Nil) map (_(scalar("")))
    now match
      case Right(ref: Ref[?]) =>
        val value = ref.cast[ZonedDateTime].value
        val after = ZonedDateTime.now
        value should (be >= before and be <= after)
      case _ => fail("Unexpected result $now")
  }

  it should "be parsed with adjuster" in {
    val before = ZonedDateTime.now.minusDays(1)
    val now = RefTypeNow().parser(Nil) map (_(scalar("-1d")))
    now match
      case Right(ref: Ref[?]) =>
        val value = ref.cast[ZonedDateTime].value
        val after = ZonedDateTime.now.minusDays(1)
        value should (be >= before and be <= after)
      case _ => fail("Unexpected result $now")
  }

  it should "be parsed with adjuster and time zone" in {
    val zone = "Europe/London"
    val zoneId = ZoneId.of(zone)
    val before = ZonedDateTime.now(zoneId).minusMonths(1)
    val now = RefTypeNow(zone).parser(Nil) map (_(scalar("-0000-01-00")))
    now match
      case Right(ref: Ref[?]) =>
        val value = ref.cast[ZonedDateTime].value
        val after = ZonedDateTime.now(zoneId).minusMonths(1)
        value.getZone shouldBe ZoneId.of(zone)
        value should (be >= before and be <= after)
      case _ => fail("Unexpected result $now")
  }
