package borscht.typed.types

import borscht.typed.{Position, ValueParser, ValueTypeConstructor}

class TestValueTypeConstructor(name: String) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueParser], position: Position): List[ValueParser] = parameters

  override protected def create(parameters: List[ValueParser]): ValueParser = TestValueType(name, parameters)
