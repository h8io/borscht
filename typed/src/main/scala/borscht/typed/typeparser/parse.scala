package borscht.typed.typeparser

import borscht.typed.{ValueParser, ValueType}

private[typed] def parse(chars: IndexedSeq[Char], types: Map[String, ValueType]): Option[ValueParser] =
  Events(chars)(RootParser(types)) flatMap (_.result)
