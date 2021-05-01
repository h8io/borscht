package borscht.typed.parsers

import borscht.typed.{ValueType, ValueTypeConstructor}

case class TestValueType(name: String, parameters: List[ValueType]) extends ValueType :
  override def apply(value: String): AnyRef = s"$name:$value"

class TestValueTypeConstructor(name: String) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueType]): List[ValueType] = parameters

  override protected def create(parameters: List[ValueType]): ValueType = TestValueType(name, parameters)
