package borscht.typed.types

import borscht.time.DateTimeAdjuster
import borscht.typed.ValueParser

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.time.{ZoneId, ZoneOffset, ZonedDateTime}

object ValueTypeDateTimeFormatter extends ValueTypePlain[DateTimeFormatter] :
  override def parse(pattern: String): DateTimeFormatter = DateTimeFormatter.ofPattern(pattern)

object ValueTypeZoneId extends ValueTypePlain[ZoneId] :
  override def parse(zone: String): ZoneId = ZoneId.of(zone)

object ValueTypeZoneOffset extends ValueTypePlain[ZoneOffset] :
  override def parse(offset: String): ZoneOffset = ZoneOffset.of(offset)

class ValueTypeNow(zone: ZoneId) extends ValueTypePlain[ZonedDateTime] :
  def this() = this(ZoneId.systemDefault())

  def this(zone: String) = this(ZoneId.of(zone))

  override def parse(adjuster: String): ZonedDateTime = ZonedDateTime.now(zone).`with`(DateTimeAdjuster(adjuster))

private trait ValueTypeTemporalAccessor(formatter: DateTimeFormatter) extends ValueTypePlain[TemporalAccessor]:
  override def parse(value: String): TemporalAccessor = formatter.parse(value)

class ValueTypeDateTime(formatter: DateTimeFormatter) extends ValueTypeTemporalAccessor(formatter):
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE_TIME)

class ValueTypeDate(formatter: DateTimeFormatter) extends ValueTypeTemporalAccessor(formatter) :
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE)

class ValueTypeTime(formatter: DateTimeFormatter) extends ValueTypeTemporalAccessor(formatter) :
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_TIME)
