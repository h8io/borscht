package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

trait ValueTypeParameterless extends AbstractValueType:
  override protected def prepare(parsers: List[ValueParser]): List[ValueParser] =
    if (parsers.isEmpty) Nil else throw WrongParameterNumber(parsers, "nothing")
