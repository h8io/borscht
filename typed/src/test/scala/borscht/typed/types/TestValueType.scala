package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

class TestValueType(name: String) extends AbstractValueType[List[ValueParser]]:
  override protected def prepare(parameters: List[ValueParser]): Either[String, List[ValueParser]] = Right(parameters)

  override protected def parser(parameters: List[ValueParser]): ValueParser = TestValueParser(name, parameters)
