package borscht.typed.types

import borscht.typed.ValueType

case class TestValueType(name: String, parameters: List[ValueType]) extends ValueType :
  override def apply(value: String): AnyRef = s"$name:$value"
