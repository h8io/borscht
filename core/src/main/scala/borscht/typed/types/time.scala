package borscht.typed.types

import borscht.Node
import borscht.time.DateTimeAdjuster

import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import java.time.{ZoneId, ZoneOffset, ZonedDateTime}

object RefTypeDateTimeFormatter extends RefTypePlain[DateTimeFormatter]:
  override protected def parse(value: String): DateTimeFormatter = DateTimeFormatter.ofPattern(value)

object RefTypeZoneId extends RefTypePlain[ZoneId]:
  override protected def parse(value: String): ZoneId = ZoneId.of(value)

object RefTypeZoneOffset extends RefTypePlain[ZoneOffset]:
  override protected def parse(value: String): ZoneOffset = ZoneOffset.of(value)

class RefTypeNow(zone: ZoneId) extends RefTypePlain[ZonedDateTime]:
  def this() = this(ZoneId.systemDefault())

  def this(zone: String) = this(ZoneId.of(zone))

  override protected def parse(value: String): ZonedDateTime = ZonedDateTime.now(zone).`with`(DateTimeAdjuster(value))

private trait RefTypeTemporalAccessor(formatter: DateTimeFormatter) extends RefTypePlain[TemporalAccessor]:
  override protected def parse(value: String): TemporalAccessor = formatter.parse(value)

class RefTypeDateTime(formatter: DateTimeFormatter) extends RefTypeTemporalAccessor(formatter):
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE_TIME)

class RefTypeDate(formatter: DateTimeFormatter) extends RefTypeTemporalAccessor(formatter):
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE)

class RefTypeTime(formatter: DateTimeFormatter) extends RefTypeTemporalAccessor(formatter):
  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_TIME)
