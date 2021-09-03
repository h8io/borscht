package borscht.typedOld.types

import borscht.parsers.{NodeParserLocale, NodeParserMessageFormat}

import java.text.MessageFormat
import java.util.Locale

object ValueTypeLocale extends ValueTypeInherited[Locale]

object ValueTypeMessageFormat extends ValueTypeInherited[MessageFormat]
