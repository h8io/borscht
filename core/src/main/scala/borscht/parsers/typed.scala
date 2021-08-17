package borscht.parsers

import borscht.*
import borscht.typed.*

given NodeParserValueParser: NodeParser[ValueParser] =
  case node => valueparser.parse(node.parse[String], node.meta.valueTypes)

given NodeParserValueRef: NodeParser[ValueRef] =
  case cfg: CfgNode if cfg.size == 1 =>
    val (tp, node) = cfg.iterator.next
    ValueRef(valueparser.parse(tp, cfg.meta.valueTypes)(node))
  case node: Node => node.parse[String] split(":", 2) match
    case Array(value) => node match
      case scalar: ScalarNode => ValueRef(scalar.value)
      case other => ValueRef(other)
    case Array(tp, value) =>
      ValueRef(new VirtualScalarNode(tp, node).parse[ValueParser](new VirtualScalarNode(value, node)))
    case unexpected => throw Error(s"Unexpected array size ${unexpected.length}")
