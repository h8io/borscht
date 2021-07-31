package borscht.typed.types

import borscht.typed.ValueParser

case class TestValueType(name: String, parameters: List[ValueParser]) extends ValueParser :
  override def apply(value: String): AnyRef = s"$name:$value"
