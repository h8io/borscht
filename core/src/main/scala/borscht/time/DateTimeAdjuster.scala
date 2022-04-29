package borscht.time

import java.time.temporal.*
import java.time.{Duration, Period}
import java.util

trait DateTimeAdjuster extends TemporalAdjuster

private class PositiveDateTimeAdjuster(period: Period, duration: Duration) extends DateTimeAdjuster:
  override def adjustInto(temporal: Temporal): Temporal = temporal.plus(period).plus(duration)

private class NegativeDateTimeAdjuster(period: Period, duration: Duration) extends DateTimeAdjuster:
  override def adjustInto(temporal: Temporal): Temporal = temporal.minus(period).minus(duration)

private object ZeroDateTimeAdjuster extends DateTimeAdjuster:
  override def adjustInto(temporal: Temporal): Temporal = temporal

object DateTimeAdjuster extends (String => DateTimeAdjuster):
  private val Sign = "(\\+|-)"

  private val DateFormat =
    s"${Sign}P?((\\d+)[Yy])?((\\d+)[Mm])?((\\d+)[Dd])?(T((\\d+)[Hh])?((\\d+)[Mm])?((\\d+)[Ss])?)?".r
  private val WeekFormat = s"${Sign}P?((\\d+)[Ww])?".r

  private val BasicFormat = s"${Sign}P?(\\d{4})(\\d{2})(\\d{2})T(\\d{2})(\\d{2})(\\d{2})".r
  private val ExtendedFormat = s"${Sign}P?((\\d+)-(\\d+)-(\\d+))?(T(\\d+):(\\d+):(\\d+))?".r

  def apply(value: String): DateTimeAdjuster =
    if value.isEmpty then ZeroDateTimeAdjuster
    else
      value match
        case DateFormat(sign, _, years, _, months, _, days, _, _, hours, _, minutes, _, seconds) =>
          apply(sign, years, months, days, hours, minutes, seconds)
        case WeekFormat(sign, _, weeks) => apply(sign, Period.ofWeeks(weeks.toInt), Duration.ZERO)
        case BasicFormat(sign, years, months, days, hours, minutes, seconds) =>
          apply(sign, years, months, days, hours, minutes, seconds)
        case ExtendedFormat(sign, _, years, months, days, _, hours, minutes, seconds) =>
          apply(sign, years, months, days, hours, minutes, seconds)
        case _ => throw IllegalArgumentException(s"$value could not be parsed")

  private def apply(
      sign: String,
      years: String | Null,
      months: String | Null,
      days: String | Null,
      hours: String | Null,
      minutes: String | Null,
      seconds: String | Null
  ): DateTimeAdjuster = apply(
    sign,
    Period.of(toInt(years), toInt(months), toInt(days)),
    Duration.ofSeconds(toLong(hours) * 3600 + toLong(minutes) * 60 + toLong(seconds))
  )

  private def toInt(value: String | Null): Int = value match
    case null          => 0
    case value: String => value.toInt

  private def toLong(value: String | Null): Long = value match
    case null          => 0
    case value: String => value.toLong

  private def apply(sign: String, period: Period, duration: Duration): DateTimeAdjuster = sign match
    case "+" => PositiveDateTimeAdjuster(period, duration)
    case "-" => NegativeDateTimeAdjuster(period, duration)
    case _   => throw IllegalStateException()
