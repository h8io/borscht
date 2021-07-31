package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

class TestValueType(name: String) extends AbstractValueType:
  override protected def prepare(parameters: List[ValueParser]): List[ValueParser] = parameters

  override protected def create(parameters: List[ValueParser]): ValueParser = TestValueParser(name, parameters)
