package borscht.typed.types

import borscht.time.DateTimeAdjuster

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

object ValueTypeConstructorDateTime extends ValueTypeConstructorParameterless(DateTimeFormatter.ISO_DATE_TIME.parse)

object ValueTypeConstructorDate extends ValueTypeConstructorParameterless(DateTimeFormatter.ISO_DATE.parse)

object ValueTypeConstructorTime extends ValueTypeConstructorParameterless(DateTimeFormatter.ISO_TIME.parse)

object ValueTypeConstructorNow extends ValueTypeConstructorParameterless(
  (adjuster: String) => ZonedDateTime.now().`with`(DateTimeAdjuster(adjuster)))
