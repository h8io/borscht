package borscht.typed.types

import borscht.Node
import borscht.time.DateTimeAdjuster

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.time.{ZoneId, ZoneOffset, ZonedDateTime}

object ValueTypeDateTimeFormatter extends ValueTypePlain[DateTimeFormatter]:
  override protected def parse(value: String): DateTimeFormatter = DateTimeFormatter.ofPattern(value)

object ValueTypeZoneId extends ValueTypePlain[ZoneId]:
  override protected def parse(value: String): ZoneId = ZoneId.of(value)

object ValueTypeZoneOffset extends ValueTypePlain[ZoneOffset]:
  override protected def parse(value: String): ZoneOffset = ZoneOffset.of(value)

class ValueTypeNow(zone: ZoneId) extends ValueTypePlain[ZonedDateTime]:
  def this() = this(ZoneId.systemDefault())

  def this(zone: String) = this(ZoneId.of(zone))

  override protected def parse(value: String): ZonedDateTime = ZonedDateTime.now(zone).`with`(DateTimeAdjuster(value))

private trait ValueTypeTemporalAccessor(formatter: DateTimeFormatter) extends ValueTypePlain[TemporalAccessor]:
  override protected def parse(value: String): TemporalAccessor = formatter.parse(value)

class ValueTypeDateTime(formatter: DateTimeFormatter) extends ValueTypeTemporalAccessor(formatter):
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE_TIME)

class ValueTypeDate(formatter: DateTimeFormatter) extends ValueTypeTemporalAccessor(formatter):
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE)

class ValueTypeTime(formatter: DateTimeFormatter) extends ValueTypeTemporalAccessor(formatter):
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_TIME)
