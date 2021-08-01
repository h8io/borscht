package borscht.typed

import borscht.*
import borscht.parsers.NodeParserValueParser
import borscht.reflect.ComponentRef

class DefaultNodeParserValueRef extends NodeParser[ValueRef]:
  def isDefinedAt(node: Node): Boolean = node match
    case cfg: CfgNode => cfg.contains("value") && (cfg.child("type") exists NodeParserValueParser.isDefinedAt)
    case _ => false

  def apply(node: Node): ValueRef = (node: @unchecked) match
    case cfg: CfgNode => cfg.child("value") map { node =>
      ValueRef(cfg[ValueParser]("type").apply(node))
    } getOrElse (throw MatchError(cfg))
