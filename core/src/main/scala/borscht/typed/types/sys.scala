package borscht.typed.types

import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

final class ValueTypeEnv extends ValueTypeStringMapper:
  override def parse(value: String): String = sys.env(value)

final class ValueTypeProp extends ValueTypeStringMapper:
  override def parse(value: String): String = sys.props(value)
