package borscht.typed.valueparser

import borscht.typed.{ValueParser, ValueType}

def parse(chars: IndexedSeq[Char], types: Map[String, ValueType]): ValueParser =
  Events(chars)(RootParser(types)).result getOrElse (throw IllegalStateException(s"Unparsable type $chars"))
