package borscht.typed.types

import borscht.Node
import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

final class ValueTypeString extends ValueTypeParameterless:
  override def parser(parsers: List[ValueParser]): ValueParser = _.parse[String]
