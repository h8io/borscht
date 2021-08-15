package borscht.typed

import borscht.*
import borscht.parsers.{NodeParserString, NodeParserValueParser}
import borscht.reflect.ComponentRef

class DefaultNodeParserValueRef extends NodeParser[ValueRef]:
  def isDefinedAt(node: Node): Boolean = true

  def apply(node: Node): ValueRef = (node: @unchecked) match
    case cfg: CfgNode => cfg.child("value") map { node =>
      ValueRef(cfg[ValueParser]("type").apply(node))
    } getOrElse (throw UndefinedValueException(cfg.position))
    case node: Node => node.parse[String] split (":", 2) match
      case Array(value) => node match
        case scalar: ScalarNode => ValueRef(scalar.value)
        case other => ValueRef(other)
      case Array(tp, value) =>
        ValueRef(new VirtualScalarNode(tp, node).parse[ValueParser](new VirtualScalarNode(value, node)))
      case unexpected => throw Error(s"Unexpected array size ${unexpected.length}")