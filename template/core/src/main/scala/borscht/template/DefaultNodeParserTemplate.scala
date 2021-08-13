package borscht.template

import borscht.*
import borscht.parsers.given
import borscht.reflect.ComponentRef
import borscht.typed.ValueRefEntries

class DefaultNodeParserTemplate(default: Option[TemplateEngine],
                                underlying: Map[String, TemplateEngine]) extends NodeParser[Template]:
  private def this(underlying: Map[String, TemplateEngine], default: Option[Node]) = this(
    default map { node =>
      val engine = node.parse[String]
      underlying.get(engine) getOrElse (throw TemplateEngineException("Unknown default engine", node.position))
    }, underlying)

  def this(cfg: CfgNode) = this(
    cfg.map[ComponentRef[TemplateEngine]]("underlying") map { (key, value) => key -> value.get },
    cfg.child("default"))

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
        (cfg.get[CfgNode](Parameters) map (ValueRefEntries(_)) foldLeft engine(template))(_ set _)
      } getOrElse {
        throw TemplateEngineException("Template is not defined", node.position)
      }
    case _ => default map (_ (node)) getOrElse {
      throw TemplateEngineException("Default template engine is not defined", node.position)
    }

  private inline def Engine = "engine"

  private inline def Template = "template"

  private inline def Parameters = "parameters"
