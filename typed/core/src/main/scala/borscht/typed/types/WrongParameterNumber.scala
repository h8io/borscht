package borscht.typed.types

import borscht.typed.{Position, ValueTypeParserException}

class WrongParameterNumber(position: Position, parameters: List[ValueType], expected: Int)
  extends ValueTypeParserException(position, s"Wrong parameters number $parameters, $expected expected")
