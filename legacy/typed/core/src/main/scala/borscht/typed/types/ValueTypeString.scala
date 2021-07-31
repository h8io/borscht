package borscht.typed.types

import borscht.typed.ValueParser

object ValueTypeString extends ValueParser :
  override def apply(value: String): AnyRef = value

object ValueTypeConstructorString extends ValueTypeConstructorParameterless(ValueTypeString)
