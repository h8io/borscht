package borscht.test

import borscht.{Meta, Node, Position}

private[test] def wrap(value: Any, meta: Meta, position: => Position): Node = value match
  case node: Node => node withMeta meta
  case map: Map[?, ?] => MockCfgNode(map map { (key, value) => key.toString -> value }, meta, position)
  case seq: Iterable[?] => MockSeqNode(seq, meta, position)
  case scalar => MockScalarNode(scalar, meta, position)
