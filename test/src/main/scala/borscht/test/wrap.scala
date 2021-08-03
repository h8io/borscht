package borscht.test

import borscht.{Meta, Node, Position}

private[test] def wrap(value: Any, meta: Meta, position: => Position): Node = value match
  case node: Node => node
  case map: Map[?, ?] => TestCfgNode(map map { (key, value) => key.toString -> value }, meta, position)
  case seq: Iterable[?] => TestSeqNode(seq, meta, position)
  case scalar: _ => TestScalarNode(scalar, meta, position)
