package borscht.typed.types

import borscht.typed.{Position, ValueParser, ValueTypeConstructor}

class ValueTypeConstructorOptionalParameter(parser: String => String) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueParser], position: Position): List[ValueParser] = parameters match
    case Nil => List(ValueTypeString)
    case _ :: Nil => parameters
    case _ => throw WrongParameterNumber(position, parameters, "none or one")

  override def create(parameters: List[ValueParser]): ValueParser = (value: String) => parameters.head.apply(parser(value))
