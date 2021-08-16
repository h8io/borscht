package borscht.typed.types

import borscht.parsers.NodeParserString
import borscht.typed.ValueParser

object ValueTypeEnv extends ValueTypeStringMapper:
  override def parse(value: String): String = sys.env(value)

object ValueTypeProp extends ValueTypeStringMapper:
  override def parse(value: String): String = sys.props(value)
