package borscht.template

import borscht.*
import borscht.parsers.{NodeParserComponentRef, NodeParserMap, NodeParserString, NodeParserValueRef}
import borscht.reflect.ComponentRef
import borscht.typed.ValueRef

class DefaultNodeParserTemplate(default: Option[TemplateEngine],
                                underlying: Map[String, TemplateEngine]) extends NodeParser[Template]:
  def this(underlying: Map[String, TemplateEngine], default: Option[String]) = this(default map underlying, underlying)

  def this(cfg: CfgNode) = this(
    cfg.map[ComponentRef[TemplateEngine]]("underlying") map { (key, value) => key -> value.get },
    cfg.get[String]("default"))

  given NodeParserTemplateEngine: NodeParser[TemplateEngine] = NodeParserString andThen underlying

  override def isDefinedAt(node: Node): Boolean = node match
    case cfg: CfgNode => cfg.get[TemplateEngine](Engine).isDefined || default.isDefined
    case _ => default.isDefined

  override def apply(node: Node): Template = node match
    case cfg: CfgNode =>
      val engine = cfg.get[TemplateEngine](Engine) orElse default getOrElse {
        throw TemplateEngineException("Both \"engine\" property and default engine are not defined", node.position)
      }
      cfg.child(Template) map { template =>
        (cfg.get[Map[String, ValueRef]](Parameters) foldLeft engine(template)) { (t, p) =>
          t.set(p.iterator map { (k, v) => k -> v.value })
        }
      } getOrElse {
        throw TemplateEngineException("Template is not defined", node.position)
      }
    case _ => default map (_(node)) getOrElse {
      throw TemplateEngineException("Default template engine is not defined", node.position)
    }

  private inline def Engine = "engine"

  private inline def Template = "template"

  private inline def Parameters = "parameters"
