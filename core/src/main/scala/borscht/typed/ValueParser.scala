package borscht.typed

import borscht.{CfgNode, Node, ScalarNode, VirtualScalarNode}
import borscht.parsers.{NodeParserString, NodeParserValueParser}

import scala.annotation.targetName

@FunctionalInterface
trait ValueParser extends (Node => Any)
