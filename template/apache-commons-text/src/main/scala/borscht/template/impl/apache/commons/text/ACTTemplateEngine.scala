package borscht.template.impl.apache.commons.text

import borscht.{Node, SeqNode}
import borscht.parsers.{NodeParserComponentRef, NodeParserList, NodeParserString}
import borscht.reflect.ComponentRef
import borscht.template.impl.apache.commons.text.renderers.Renderer
import borscht.template.{Template, TemplateEngine}
import org.apache.commons.text.StringSubstitutor
import org.apache.commons.text.lookup.StringLookupFactory

final class ACTTemplateEngine(substitutor: StringSubstitutor,
                              renderers: List[Renderer],
                              valueFormat: ValueFormat = ValueFormat.default) extends TemplateEngine:
  def this() = this(ACTTemplateEngine.DefaultStringSubstitutor, Nil, ValueFormat.default)

  def this(renderers: List[Renderer]) = this(ACTTemplateEngine.DefaultStringSubstitutor, renderers, ValueFormat.default)

  def this(renderers: SeqNode) = this(renderers.list[ComponentRef[Renderer]] map (_.get))

  override def apply(template: Node): Template =
    ACTTemplate(substitutor, template.as[String], renderers, valueFormat)

object ACTTemplateEngine:
  object DefaultStringSubstitutor extends StringSubstitutor(
    StringLookupFactory.INSTANCE.nullStringLookup(),
    StringSubstitutor.DEFAULT_PREFIX,
    StringSubstitutor.DEFAULT_SUFFIX,
    StringSubstitutor.DEFAULT_ESCAPE,
    StringSubstitutor.DEFAULT_VALUE_DELIMITER)
