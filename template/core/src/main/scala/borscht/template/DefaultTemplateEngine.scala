package borscht.template

import borscht.{CfgNode, CfgNodeParserException, Node, ScalarNode}
import borscht.parsers.{NodeParserComponentRef, NodeParserString}
import borscht.reflect.ComponentRef

class DefaultTemplateEngine(default: Option[TemplateEngine],
                            underlying: Map[String, TemplateEngine]) extends TemplateEngine:
  def this(underlying: Map[String, TemplateEngine], default: Option[String]) = this(default map underlying, underlying)

  def this(cfg: CfgNode) = this(
    cfg.map[ComponentRef[TemplateEngine]]("underlying") map { (key, value) => key -> value.get },
    cfg.get[String]("default"))

  def apply(node: Node): Template = (node match
    case cfg: CfgNode => cfg.get[String]("engine") map underlying getOrElse {
        throw TemplateEngineException("Both \"engine\" property and default engine are not defined", node.position)
      }
    case _ => default getOrElse {
      throw TemplateEngineException("Default template engine is not defined", node.position)
    })(node)
