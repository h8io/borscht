package borscht.typed.types

import borscht.typed.ValueType

object ValueTypeString extends ValueType :
  override def apply(value: String): AnyRef = value

object ValueTypeConstructorString extends ValueTypeConstructorParameterless(ValueTypeString)
