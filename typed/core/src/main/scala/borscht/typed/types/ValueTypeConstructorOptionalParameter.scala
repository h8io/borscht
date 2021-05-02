package borscht.typed.types

import borscht.typed.{Position, ValueType, ValueTypeConstructor}

class ValueTypeConstructorOptionalParameter(parser: String => String) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueType], position: Position): List[ValueType] = parameters match
    case Nil => List(ValueTypeString)
    case _ :: Nil => parameters
    case _ => throw WrongParameterNumber(position, parameters, "none or one")

  override def create(parameters: List[ValueType]): ValueType = (value: String) => parameters.head.apply(parser(value))
