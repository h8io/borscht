package borscht.parsers

import borscht.*
import borscht.typed.*

given NodeParserValueParser: NodeParser[ValueParser] =
  case node =>
    val tp = node.parse[String]
    valueparser.parse(tp, node.meta.valueTypes) getOrElse { throw UnknownValueTypeException(tp, node.position) }

given NodeParserValueRef: NodeParser[ValueRef] =
  case cfg: CfgNode => cfg.child("value") map { node =>
    ValueRef(cfg.get[ValueParser]("type") map (_.apply(node)) getOrElse node)
  } getOrElse (throw UndefinedValueException(cfg.position))
  case node: Node => node.parse[String] split(":", 2) match
    case Array(value) => node match
      case scalar: ScalarNode => ValueRef(scalar.value)
      case other => ValueRef(other)
    case Array(tp, value) =>
      ValueRef(new VirtualScalarNode(tp, node).parse[ValueParser](new VirtualScalarNode(value, node)))
    case unexpected => throw Error(s"Unexpected array size ${unexpected.length}")
