package borscht.typed.types

import borscht.{Node, ScalarNode}
import borscht.parsers.given
import borscht.typed.ValueParser

import java.lang.{Byte as jByte, Double as jDouble, Float as jFloat, Integer as jInt, Long as jLong, Short as jShort}

object ValueTypeBigInt extends ValueTypeInherited[BigInt]

object ValueTypeBigDecimal extends ValueTypeInherited[BigDecimal]


object ValueTypeByte extends ValueTypeInherited[Byte]

object ValueTypeJByte extends ValueTypePlain[jByte]:
  override protected def parse(value: String): jByte = jByte.valueOf(value)


object ValueTypeDouble extends ValueTypeInherited[Double]

object ValueTypeJDouble extends ValueTypePlain[jDouble]:
  override protected def parse(value: String): jDouble = jDouble.valueOf(value)


object ValueTypeFloat extends ValueTypeInherited[Float]

object ValueTypeJFloat extends ValueTypePlain[jFloat]:
  override protected def parse(value: String): jFloat = jFloat.valueOf(value)


object ValueTypeInt extends ValueTypeInherited[Int]

object ValueTypeJInt extends ValueTypePlain[jInt]:
  override protected def parse(value: String): jInt = jInt.valueOf(value)


object ValueTypeLong extends ValueTypeInherited[Long]

object ValueTypeJLong extends ValueTypePlain[jLong]:
  override protected def parse(value: String): jLong = jLong.valueOf(value)


object ValueTypeShort extends ValueTypeInherited[Short]

object ValueTypeJShort extends ValueTypePlain[jShort]:
  override protected def parse(value: String): jShort = jShort.valueOf(value)
