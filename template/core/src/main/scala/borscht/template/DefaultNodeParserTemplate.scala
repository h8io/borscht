package borscht.template

import borscht.*
import borscht.parsers.given
import borscht.reflect.ComponentRef
import borscht.typed.ValueRefEntries

class DefaultNodeParserTemplate(default: Option[TemplateEngine],
                                underlying: Map[String, TemplateEngine]) extends NodeParser[Template]:
  private def this(underlying: Map[String, TemplateEngine], default: Option[String]) = this(
    default map { engine =>
      underlying.get(engine) getOrElse (throw IllegalArgumentException("Unknown default engine $engine"))
    }, underlying)

  def this(underlying: Map[String, TemplateEngine]) = this(underlying, None)

  def this(underlying: Map[String, TemplateEngine], default: String) = this(underlying, Some(default))

  def this(underlying: CfgNode) = this(DefaultNodeParserTemplate.underlying(underlying))

  def this(underlying: CfgNode, default: String) = this(DefaultNodeParserTemplate.underlying(underlying), default)

  override def isDefinedAt(node: Node): Boolean = node match
    case cfg: CfgNode => (cfg.get[String](Engine) flatMap underlying.get).isDefined || default.isDefined
    case _ => default.isDefined

  override def apply(node: Node): Template = node match
    case cfg: CfgNode =>
      val engine = cfg.child(Engine) map { name =>
        underlying.get(name.as[String]) getOrElse {
          throw TemplateEngineException("The template engine is unknown", name.position)
        }
      } orElse default getOrElse {
        throw TemplateEngineException("Both \"engine\" property and default engine are not defined", node.position)
      }
      cfg.child(Template) map { template =>
        (cfg.get[CfgNode](Parameters) map (ValueRefEntries(_)) foldLeft engine(template))(_ set _)
      } getOrElse {
        throw TemplateEngineException("Template is not defined", node.position)
      }
    case _ => default map (_ (node)) getOrElse {
      throw TemplateEngineException("The default template engine is unknown", node.position)
    }

  private inline def Engine = "engine"

  private inline def Template = "template"

  private inline def Parameters = "parameters"

private object DefaultNodeParserTemplate:
  private def underlying(cfg: CfgNode) = cfg.map[ComponentRef[TemplateEngine]]() map { (name, component) =>
    name -> component.get
  }

