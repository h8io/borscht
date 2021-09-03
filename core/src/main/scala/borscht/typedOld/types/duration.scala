package borscht.typedOld.types

import borscht.parsers.{NodeParserDuration, NodeParserFiniteDuration}

import scala.concurrent.duration.{Duration, FiniteDuration}

object ValueTypeDuration extends ValueTypeInherited[Duration]

object ValueTypeFiniteDuration extends ValueTypeInherited[FiniteDuration]
