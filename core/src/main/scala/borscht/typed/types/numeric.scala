package borscht.typed.types

import borscht.ScalarNode
import borscht.parsers.given
import borscht.typed.ValueParser

import java.lang.{Byte as jByte, Double as jDouble, Float as jFloat, Integer as jInt, Long as jLong, Short as jShort}

object ValueTypeBigInt extends ValueTypeInheritedWithStringParser[BigInt]:
  override def parse(value: String): BigInt = BigInt(value)

object ValueTypeBigDecimal extends ValueTypeInheritedWithStringParser[BigDecimal]:
  override def parse(value: String): BigDecimal = BigDecimal(value)


object ValueTypeByte extends ValueTypeInheritedWithStringParser[Byte]:
  override def parse(value: String): Byte = value.toByte

object ValueTypeJByte extends ValueTypePlain[jByte]:
  override def parse(value: String): jByte = jByte.valueOf(value)


object ValueTypeDouble extends ValueTypeInheritedWithStringParser[Double]:
  override def parse(value: String): Double = value.toDouble

object ValueTypeJDouble extends ValueTypePlain[jDouble]:
  override def parse(value: String): jDouble = jDouble.valueOf(value)


object ValueTypeFloat extends ValueTypeInheritedWithStringParser[Float]:
  override def parse(value: String): Float = value.toFloat

object ValueTypeJFloat extends ValueTypePlain[jFloat]:
  override def parse(value: String): jFloat = jFloat.valueOf(value)


object ValueTypeInt extends ValueTypeInheritedWithStringParser[Int]:
  override def parse(value: String): Int = value.toInt

object ValueTypeJInt extends ValueTypePlain[jInt]:
  override def parse(value: String): jInt = jInt.valueOf(value)


object ValueTypeLong extends ValueTypeInheritedWithStringParser[Long]:
  override def parse(value: String): Long = value.toLong

object ValueTypeJLong extends ValueTypePlain[jLong]:
  override def parse(value: String): jLong = jLong.valueOf(value)


object ValueTypeShort extends ValueTypeInheritedWithStringParser[Short]:
  override def parse(value: String): Short = value.toShort

object ValueTypeJShort extends ValueTypePlain[jShort]:
  override def parse(value: String): jShort = jShort.valueOf(value)
