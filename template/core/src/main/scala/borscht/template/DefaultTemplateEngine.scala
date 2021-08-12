package borscht.template

import borscht.{CfgNode, Node, ScalarNode}
import borscht.parsers.given
import borscht.reflect.ComponentRef

class DefaultTemplateEngine(default: Option[TemplateEngine],
                            underlying: Map[String, TemplateEngine]) extends TemplateEngine:
  def this(underlying: Map[String, TemplateEngine], default: Option[String]) = this(default map underlying, underlying)

  def this(cfg: CfgNode) = this(
    cfg.map[ComponentRef[TemplateEngine]]("underlying") map { (key, value) => key -> value.get },
    cfg.get[String]("default"))

  def apply(node: Node): Template = node match
    case scalar: ScalarNode => ???
    case cfg: CfgNode => ???
    case _ => ???
