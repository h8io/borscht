package borscht.typed

import borscht.parsers.NodeParserString
import borscht.typed.parser.{Events, RootParser}
import borscht.virtual.VirtualScalarNode
import borscht.{CfgNode, Node, NodeParser, ScalarNode}

import scala.reflect.ClassTag

def parseType(node: Node): NodeParser[Ref[?]] = parseType(node.as[String], node.meta.refTypes)

def parseType(definition: String, types: Map[String, RefType]): NodeParser[Ref[?]] =
  Events(definition)(RootParser(types)).result getOrElse (throw IllegalStateException(s"Unparsable type $definition"))

def parseRef(node: Node): Ref[?] = node match
  case cfg: CfgNode if cfg.size == 1 =>
    val (tp, node) = cfg.iterator.next
    parseType(tp, cfg.meta.refTypes)(node)
  case scalar: ScalarNode => scalar.value match
    case str: String => str split (":", 2) match
      case Array(value) => RefObj[String](str)
      case Array(tp, value) => parseType(tp, scalar.meta.refTypes)(new VirtualScalarNode(value, scalar))
      case unexpected => throw Error(s"Unexpected array size ${unexpected.length}")
    case value => Ref(value)(using ClassTag(value.getClass))
  case node: Node => RefObj(node)(using ClassTag(node.getClass))
