package borscht.typed.types

import borscht.ScalarNode
import borscht.typed.ValueParser

import java.lang.{Byte as jByte, Double as jDouble, Float as jFloat, Integer as jInt, Long as jLong, Short as jShort}

final class ValueTypeBigInt extends ValueTypePlain[BigInt]:
  override def parse(value: String): BigInt = BigInt(value)

final class ValueTypeBigDecimal extends ValueTypePlain[BigDecimal]:
  override def parse(value: String): BigDecimal = BigDecimal(value)


final class ValueTypeByte extends ValueTypePlain[Byte]:
  override def parse(value: String): Byte = value.toByte

final class ValueTypeJByte extends ValueTypePlain[jByte]:
  override def parse(value: String): jByte = jByte.valueOf(value)


final class ValueTypeDouble extends ValueTypePlain[Double]:
  override def parse(value: String): Double = value.toDouble

final class ValueTypeJDouble extends ValueTypePlain[jDouble]:
  override def parse(value: String): jDouble = jDouble.valueOf(value)


final class ValueTypeFloat extends ValueTypePlain[Float]:
  override def parse(value: String): Float = value.toFloat

final class ValueTypeJFloat extends ValueTypePlain[jFloat]:
  override def parse(value: String): jFloat = jFloat.valueOf(value)


final class ValueTypeInt extends ValueTypePlain[Int]:
  override def parse(value: String): Int = value.toInt

final class ValueTypeJInt extends ValueTypePlain[jInt]:
  override def parse(value: String): jInt = jInt.valueOf(value)


final class ValueTypeLong extends ValueTypePlain[Long]:
  override def parse(value: String): Long = value.toLong

final class ValueTypeJLong extends ValueTypePlain[jLong]:
  override def parse(value: String): jLong = jLong.valueOf(value)


final class ValueTypeShort extends ValueTypePlain[Short]:
  override def parse(value: String): Short = value.toShort

final class ValueTypeJShort extends ValueTypePlain[jShort]:
  override def parse(value: String): jShort = jShort.valueOf(value)
