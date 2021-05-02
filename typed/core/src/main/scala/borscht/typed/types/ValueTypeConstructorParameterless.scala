package borscht.typed.types

import borscht.typed.{Position, ValueType, ValueTypeConstructor}

class ValueTypeConstructorParameterless(`type`: ValueType) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueType], position: Position): List[ValueType] =
    if (parameters.isEmpty) Nil else throw WrongParameterNumber(position, parameters, "0")

  override protected def create(parameters: List[ValueType]): ValueType = `type`
  
