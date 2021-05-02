package borscht.typed.types

import borscht.typed.{Position, ValueType, ValueTypeConstructor}

class TestValueTypeConstructor(name: String) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueType], position: Position): List[ValueType] = parameters

  override protected def create(parameters: List[ValueType]): ValueType = TestValueType(name, parameters)
