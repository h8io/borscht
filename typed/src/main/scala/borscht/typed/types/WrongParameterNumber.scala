package borscht.typed.types

import borscht.typed.ValueParser

class WrongParameterNumber(parameters: List[ValueParser], expected: String)
  extends RuntimeException(s"Wrong parameters number $parameters, $expected expected")
