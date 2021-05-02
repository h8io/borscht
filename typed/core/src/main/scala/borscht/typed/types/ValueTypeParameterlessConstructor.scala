package borscht.typed.types

import borscht.typed.Position

trait ValueTypeParameterlessConstructor extends ValueTypeConstructor :
  override protected final def validate(parameters: List[ValueType], position: Position): List[ValueType] =
    if (parameters.isEmpty) Nil else throw WrongParameterNumber(position, parameters, 0)
