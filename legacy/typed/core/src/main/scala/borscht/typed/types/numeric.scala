package borscht.typed.types

object ValueTypeConstructorBigInt extends ValueTypeConstructorParameterless(BigInt.apply(_: String))

object ValueTypeConstructorBigDecimal extends ValueTypeConstructorParameterless(BigDecimal.apply(_: String))

object ValueTypeConstructorByte extends ValueTypeConstructorParameterless(java.lang.Byte.valueOf)

object ValueTypeConstructorDouble extends ValueTypeConstructorParameterless(java.lang.Double.valueOf)

object ValueTypeConstructorFloat extends ValueTypeConstructorParameterless(java.lang.Float.valueOf)

object ValueTypeConstructorInteger extends ValueTypeConstructorParameterless(java.lang.Integer.valueOf)

object ValueTypeConstructorLong extends ValueTypeConstructorParameterless(java.lang.Long.valueOf)

object ValueTypeConstructorShort extends ValueTypeConstructorParameterless(java.lang.Short.valueOf)
