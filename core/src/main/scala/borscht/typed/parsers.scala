package borscht.typed

import borscht.{CfgNode, Node, ScalarNode, VirtualScalarNode}
import borscht.parsers.{NodeParserString, NodeParserValueParser}
import borscht.typed.valueparser.{Events, RootParser}

def parseType(definition: String, types: Map[String, ValueType]): ValueParser =
  Events(definition)(RootParser(types)).result getOrElse (throw IllegalStateException(s"Unparsable type $definition"))

def parseValue(node: Node): Any = node match
  case cfg: CfgNode if cfg.size == 1 =>
    val (tp, node) = cfg.iterator.next
    parseType(tp, cfg.meta.valueTypes)(node)
  case node: Node => node.parse[String] split(":", 2) match
    case Array(value) => node match
      case scalar: ScalarNode => scalar.value
      case other => other
    case Array(tp, value) => parseType(tp, node.meta.valueTypes)(new VirtualScalarNode(value, node))
    case unexpected => throw Error(s"Unexpected array size ${unexpected.length}")
