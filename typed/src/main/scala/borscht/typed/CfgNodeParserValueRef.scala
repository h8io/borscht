package borscht.typed

import borscht.*
import borscht.parsers.NodeParserValueParser
import borscht.reflect.ComponentRef

class CfgNodeParserValueRef extends NodeParser[ValueRef]:
  def isDefinedAt(node: Node): Boolean = node match
    case cfg: CfgNode => cfg.child("type") map NodeParserValueParser.isDefinedAt getOrElse false
    case _ => false

  def apply(node: Node): ValueRef = (node: @unchecked) match
    case cfg: CfgNode => ???
