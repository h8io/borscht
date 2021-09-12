package borscht.typed.types

import borscht.Node
import borscht.parsers.given
import borscht.time.DateTimeAdjuster

import java.time.*
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

object RefTypeDateTimeFormatter extends RefTypePlain[DateTimeFormatter]:
  override protected def parse(value: String): DateTimeFormatter = DateTimeFormatter.ofPattern(value)


object RefTypeZoneId extends RefTypePlain[ZoneId]:
  override protected def parse(value: String): ZoneId = ZoneId.of(value)


object RefTypeZoneOffset extends RefTypePlain[ZoneOffset]:
  override protected def parse(value: String): ZoneOffset = ZoneOffset.of(value)


final class RefTypeNow(zone: ZoneId) extends RefTypePlain[ZonedDateTime]:
  def this() = this(ZoneId.systemDefault())

  def this(zone: String) = this(ZoneId.of(zone))

  override protected def parse(value: String): ZonedDateTime = ZonedDateTime.now(zone).`with`(DateTimeAdjuster(value))


private trait RefTypeAbstractTemporalAccessor[T <: TemporalAccessor](formatter: DateTimeFormatter,
                                                                     cast: TemporalAccessor => T)
  extends RefTypePlain[TemporalAccessor]:

  override protected def parse(value: String): T = cast(formatter.parse(value))


final class RefTypeTemporalAccessor(formatter: DateTimeFormatter)
  extends RefTypeAbstractTemporalAccessor[TemporalAccessor](formatter, identity):

  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE_TIME)


final class RefTypeZonedDateTime(formatter: DateTimeFormatter)
  extends RefTypeAbstractTemporalAccessor[ZonedDateTime](formatter, ZonedDateTime.from):

  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE_TIME)


final class RefTypeOffsetDateTime(formatter: DateTimeFormatter)
  extends RefTypeAbstractTemporalAccessor[OffsetDateTime](formatter, OffsetDateTime.from):

  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE_TIME)


final class RefTypeLocalDateTime(formatter: DateTimeFormatter)
  extends RefTypeAbstractTemporalAccessor[LocalDateTime](formatter, LocalDateTime.from):

  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE_TIME)


final class RefTypeLocalDate(formatter: DateTimeFormatter)
  extends RefTypeAbstractTemporalAccessor[LocalDate](formatter, LocalDate.from):

  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_DATE)


final class RefTypeOffsetTime(formatter: DateTimeFormatter)
  extends RefTypeAbstractTemporalAccessor[OffsetTime](formatter, OffsetTime.from):

  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_TIME)


final class RefTypeLocalTime(formatter: DateTimeFormatter)
  extends RefTypeAbstractTemporalAccessor[LocalTime](formatter, LocalTime.from):

  def this(pattern: String) = this(DateTimeFormatter.ofPattern(pattern))

  def this() = this(DateTimeFormatter.ISO_TIME)


object RefTypeInstant extends RefTypeInherited[Instant]

object RefTypeMonth extends RefTypeInherited[Month]

object RefTypeMonthDay extends RefTypeInherited[MonthDay]

object RefTypeYear extends RefTypeInherited[Year]

object RefTypeYearMonth extends RefTypeInherited[YearMonth]

object RefTypeDayOfWeek extends RefTypeInherited[DayOfWeek]

object RefTypePeriod extends RefTypeInherited[Period]

object RefTypeJavaDuration extends RefTypeInherited[Duration]
