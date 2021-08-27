package borscht.typed.types

import borscht.typed.ValueType

val DefaultValueTypes: Map[String, ValueType] = Map(
  "?" -> ValueTypeAny,
  "any" -> ValueTypeAny,
  
  "$" -> ValueTypeString,
  "str" -> ValueTypeString,

  "_" -> ValueTypeNode,
  "node" -> ValueTypeNode,
  "component" -> ValueTypeComponentRef,

  "list" -> ValueTypeList,
  "map" -> ValueTypeMap,

  "env" -> ValueTypeEnv,
  "prop" -> ValueTypeProp,

  "bool" -> ValueTypeBoolean,
  "boolean" -> ValueTypeBoolean,
  "jbool" -> ValueTypeJBoolean,
  "jboolean" -> ValueTypeJBoolean,

  "char" -> ValueTypeChar,
  "jchar" -> ValueTypeJChar,

  "bigint" -> ValueTypeBigInt,
  "bigdecimal" -> ValueTypeBigDecimal,

  "byte" -> ValueTypeByte,
  "jbyte" -> ValueTypeJByte,
  "double" -> ValueTypeDouble,
  "jdouble" -> ValueTypeJDouble,
  "float" -> ValueTypeFloat,
  "jfloat" -> ValueTypeJFloat,
  "int" -> ValueTypeInt,
  "jint" -> ValueTypeJInt,
  "long" -> ValueTypeLong,
  "jlong" -> ValueTypeJLong,
  "short" -> ValueTypeShort,
  "jshort" -> ValueTypeJShort,

  "datetime-formatter" -> ValueTypeDateTimeFormatter,
  "tz" -> ValueTypeZoneId,
  "timezone" -> ValueTypeZoneId,
  "tz-offset" -> ValueTypeZoneOffset,
  "timezone-offset" -> ValueTypeZoneOffset,
  "now" -> ValueTypeNow(),
  "datetime" -> ValueTypeDateTime(),
  "date" -> ValueTypeDate(),
  "time" -> ValueTypeTime(),

  "locale" -> ValueTypeLocale,
  "message-format" -> ValueTypeMessageFormat,

  "pattern" -> ValueTypePattern,
  "re" -> ValueTypeRegex,
  "regex" -> ValueTypeRegex,

  "duration" -> ValueTypeDuration,
  "finite-duration" -> ValueTypeFiniteDuration)
