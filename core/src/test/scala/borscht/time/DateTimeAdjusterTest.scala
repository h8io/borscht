package borscht.time

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.ZonedDateTime

class DateTimeAdjusterTest extends AnyFlatSpec with Matchers:
  private val InitialDateTime = ZonedDateTime.now()

  "DateTimeAdjuster" should "parse date and time format with prefix" in {
    DateTimeAdjuster("+P1Y2M3DT4H5M6S").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .plusYears(1).plusMonths(2).plusDays(3)
      .plusHours(4).plusMinutes(5).plusSeconds(6)
  }

  it should "parse date and time format without prefix" in {
    DateTimeAdjuster("-1y2m3dT4h5m6s").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .minusYears(1).minusMonths(2).minusDays(3)
      .minusHours(4).minusMinutes(5).minusSeconds(6)
  }

  it should "parse date format" in {
    DateTimeAdjuster("-P2M3d").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .minusMonths(2).minusDays(3)
  }

  it should "parse time format" in {
    DateTimeAdjuster("-T4h6S").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .minusHours(4).minusSeconds(6)
  }

  it should "parse week format with prefix" in {
    DateTimeAdjuster("-P2w").adjustInto(InitialDateTime) shouldEqual InitialDateTime.minusDays(14)
  }

  it should "parse week format without prefix" in {
    DateTimeAdjuster("+2W").adjustInto(InitialDateTime) shouldEqual InitialDateTime.plusDays(14)
  }

  it should "parse basic format with prefix" in {
    DateTimeAdjuster("-P00010203T040506").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .minusYears(1).minusMonths(2).minusDays(3)
      .minusHours(4).minusMinutes(5).minusSeconds(6)
  }

  it should "parse basic format without prefix" in {
    DateTimeAdjuster("+00010203T040506").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .plusYears(1).plusMonths(2).plusDays(3)
      .plusHours(4).plusMinutes(5).plusSeconds(6)
  }

  it should "parse Y-M-DTh-m-s format with prefix" in {
    DateTimeAdjuster("+P1-2-3T4:5:6").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .plusYears(1).plusMonths(2).plusDays(3)
      .plusHours(4).plusMinutes(5).plusSeconds(6)
  }

  it should "parse Y-M-DTh-m-s format without prefix" in {
    DateTimeAdjuster("-1-2-3T4:5:6").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .minusYears(1).minusMonths(2).minusDays(3)
      .minusHours(4).minusMinutes(5).minusSeconds(6)
  }

  it should "parse Y-M-D format" in {
    DateTimeAdjuster("-P1-2-3").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .minusYears(1).minusMonths(2).minusDays(3)
  }

  it should "parse Th:m:s format" in {
    DateTimeAdjuster("+T4:5:6").adjustInto(InitialDateTime) shouldEqual InitialDateTime
      .plusHours(4).plusMinutes(5).plusSeconds(6)
  }

  it should "parse empty string" in {
    DateTimeAdjuster("").adjustInto(InitialDateTime) shouldEqual InitialDateTime
  }
