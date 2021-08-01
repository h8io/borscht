package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

class TestValueType(name: String) extends AbstractValueType:
  override protected def prepare(parsers: List[ValueParser]): List[ValueParser] = parsers

  override protected def parser(parsers: List[ValueParser]): ValueParser = TestValueParser(name, parsers)
