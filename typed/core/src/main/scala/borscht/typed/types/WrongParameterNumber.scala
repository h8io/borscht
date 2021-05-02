package borscht.typed.types

import borscht.typed.{Position, ValueType, ValueTypeParserException}

class WrongParameterNumber(position: Position, parameters: List[ValueType], expected: String)
  extends ValueTypeParserException(position, s"Wrong parameters number $parameters, $expected expected")
