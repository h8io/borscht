package borscht.typed.types

import borscht.typed.{Position, ValueParser, ValueTypeParserException}

class WrongParameterNumber(position: Position, parameters: List[ValueParser], expected: String)
  extends ValueTypeParserException(position, s"Wrong parameters number $parameters, $expected expected")
