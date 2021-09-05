package borscht.typed.types

import borscht.typed.RefType

val DefaultRefTypes: Map[String, RefType] = Map(
  "?" -> RefTypeAny,
  "any" -> RefTypeAny,
  
  "$" -> RefTypeString,
  "str" -> RefTypeString,

  "_" -> RefTypeNode,
  "node" -> RefTypeNode,
  // "component" -> RefTypeComponentRef,

  // "list" -> RefTypeList,
  // "map" -> RefTypeMap,

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
  "datetime" -> RefTypeDateTime(),
  "date" -> RefTypeDate(),
  "time" -> RefTypeTime(),

  "locale" -> RefTypeLocale,
  "message-format" -> RefTypeMessageFormat,

  "pattern" -> RefTypePattern,
  "re" -> RefTypeRegex,
  "regex" -> RefTypeRegex,

  "duration" -> RefTypeDuration,
  "finite-duration" -> RefTypeFiniteDuration)
