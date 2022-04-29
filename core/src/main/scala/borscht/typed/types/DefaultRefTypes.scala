package borscht.typed.types

import borscht.typed.RefType

val DefaultRefTypes: Map[String, RefType] = {
  val refTypeZonedDateTime = RefTypeZonedDateTime()
  val refTypeLocalDate = RefTypeLocalDate()
  val refTypeOffsetTime = RefTypeOffsetTime()

  Map(
    "?" -> RefTypeAny,
    "any" -> RefTypeAny,
    "$" -> RefTypeString,
    "str" -> RefTypeString,
    "string" -> RefTypeString,
    "_" -> RefTypeNode,
    "node" -> RefTypeNode,
    "component" -> RefTypeComponent,
    "class" -> RefTypeClass,
    "list" -> RefTypeList,
    "map" -> RefTypeMap,
    "opt" -> RefTypeOption,
    "env" -> RefTypeEnv,
    "prop" -> RefTypeProp,
    "bool" -> RefTypeBoolean,
    "boolean" -> RefTypeBoolean,
    "char" -> RefTypeChar,
    "bigint" -> RefTypeBigInt,
    "bigdecimal" -> RefTypeBigDecimal,
    "byte" -> RefTypeByte,
    "double" -> RefTypeDouble,
    "float" -> RefTypeFloat,
    "int" -> RefTypeInt,
    "long" -> RefTypeLong,
    "short" -> RefTypeShort,
    "datetime-formatter" -> RefTypeDateTimeFormatter,
    "tz" -> RefTypeZoneId,
    "timezone" -> RefTypeZoneId,
    "tz-offset" -> RefTypeZoneOffset,
    "timezone-offset" -> RefTypeZoneOffset,
    "now" -> RefTypeNow(),
    "temporal-accessor" -> RefTypeTemporalAccessor(),
    "datetine" -> refTypeZonedDateTime,
    "datetime-zoned" -> refTypeZonedDateTime,
    "datetime-offset" -> RefTypeOffsetDateTime(),
    "datetime-local" -> RefTypeLocalDateTime(),
    "date" -> refTypeLocalDate,
    "date-local" -> refTypeLocalDate,
    "time" -> refTypeOffsetTime,
    "time-offset" -> refTypeOffsetTime,
    "time-local" -> RefTypeLocalTime(),
    "instant" -> RefTypeInstant,
    "month" -> RefTypeMonth,
    "monthday" -> RefTypeMonthDay,
    "year" -> RefTypeYear,
    "yearmonth" -> RefTypeYearMonth,
    "dayofweek" -> RefTypeDayOfWeek,
    "period" -> RefTypePeriod,
    "duration-java" -> RefTypeJavaDuration,
    "locale" -> RefTypeLocale,
    "message-format" -> RefTypeMessageFormat,
    "pattern" -> RefTypePattern,
    "re" -> RefTypeRegex,
    "regex" -> RefTypeRegex,
    "duration" -> RefTypeDuration,
    "finite-duration" -> RefTypeFiniteDuration
  )
}
