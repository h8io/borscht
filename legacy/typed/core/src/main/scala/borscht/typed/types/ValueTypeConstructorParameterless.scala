package borscht.typed.types

import borscht.typed.{Position, ValueParser, ValueTypeConstructor}

class ValueTypeConstructorParameterless(`type`: ValueParser) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueParser], position: Position): List[ValueParser] =
    if (parameters.isEmpty) Nil else throw WrongParameterNumber(position, parameters, "0")

  override protected def create(parameters: List[ValueParser]): ValueParser = `type`
  
