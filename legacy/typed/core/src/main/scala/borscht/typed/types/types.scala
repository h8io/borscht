package borscht.typed.types

import borscht.typed.ValueTypeConstructor

val StringType: Map[String, ValueTypeConstructor] = Map(
  "_" -> ValueTypeConstructorString,
  "str" -> ValueTypeConstructorString)

val NumericTypes: Map[String, ValueTypeConstructor] = Map(
  "bigint" -> ValueTypeConstructorBigInt,
  "bigdecimal" -> ValueTypeConstructorBigDecimal,
  "byte" -> ValueTypeConstructorByte,
  "double" -> ValueTypeConstructorDouble,
  "float" -> ValueTypeConstructorFloat,
  "int" -> ValueTypeConstructorInteger,
  "long" -> ValueTypeConstructorLong,
  "short" -> ValueTypeConstructorShort)

val DateTimeTypes: Map[String, ValueTypeConstructor] = Map(
  "datetime" -> ValueTypeConstructorDateTime,
  "date" -> ValueTypeConstructorDate,
  "time" -> ValueTypeConstructorTime,
  "now" -> ValueTypeConstructorNow)

val MiscTypes: Map[String, ValueTypeConstructor] = Map(
  "env" -> ValueTypeConstructorEnv,
  "prop" -> ValueTypeConstructorProp)

val DefaultTypes: Map[String, ValueTypeConstructor] = StringType ++ NumericTypes ++ DateTimeTypes ++ MiscTypes
