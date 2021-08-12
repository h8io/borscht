package borscht.template.impl.st4

import borscht.template.Template
import borscht.template.impl.st4.renderers._
import borscht.template.TemplateEngine
import org.stringtemplate.v4.{AttributeRenderer, ST, STGroup}

import java.time.temporal.TemporalAccessor

final class ST4TemplateParser(group: STGroup) extends TemplateEngine:
  override def apply(template: String): Template = ST4Template(ST(group, template))

object ST4TemplateParser:
  def apply(group: STGroup): ST4TemplateParser = new ST4TemplateParser(group)

  def apply(renderers: IterableOnce[Renderer[_]] = DefaultAttributeRenderers): ST4TemplateParser =
    val group = STGroup()
    renderers.iterator foreach (_(group))
    ST4TemplateParser(group)

  val DefaultAttributeRenderers: List[Renderer[_]] = List(TemporalAccessorRenderer)