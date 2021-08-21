package borscht.typed

import borscht.parsers.{NodeParserString, NodeParserValueParser}
import borscht.typed.valueparser.{Events, RootParser}
import borscht.virtual.VirtualScalarNode
import borscht.{CfgNode, Node, ScalarNode}

def parseType(definition: String, types: Map[String, ValueType]): ValueParser =
  Events(definition)(RootParser(types)).result getOrElse (throw IllegalStateException(s"Unparsable type $definition"))

def parseValue(node: Node): Any = node match
  case cfg: CfgNode if cfg.size == 1 =>
    val (tp, node) = cfg.iterator.next
    parseType(tp, cfg.meta.valueTypes)(node)
  case scalar: ScalarNode => scalar.asString split(":", 2) match
    case Array(value) => scalar.value
    case Array(tp, value) => parseType(tp, scalar.meta.valueTypes)(new VirtualScalarNode(value, scalar))
    case unexpected => throw Error(s"Unexpected array size ${unexpected.length}")
  case node: Node => node
