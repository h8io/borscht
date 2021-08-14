package borscht.template.impl.apache.commons.text

import borscht.template.impl.apache.commons.text.renderers.{Renderer, TemporalRenderer}
import borscht.template.{Template, TemplateEngine}
import org.apache.commons.text.StringSubstitutor
import org.apache.commons.text.lookup.{StringLookup, StringLookupFactory}
import org.apache.commons.text.matcher.StringMatcher

final class ACTTemplateEngine(substitutor: StringSubstitutor,
                              renderer: Renderer,
                              valueFormat: ValueFormat = ValueFormat.default) extends TemplateEngine:
  override def apply(template: String): Template =
    ACTTemplate(substitutor, template, renderer, valueFormat)

object ACTTemplateEngine:
  def apply(renderer: Renderer = TemporalRenderer,
            valueFormat: ValueFormat = ValueFormat.default,
            stringLookup: StringLookup = StringLookupFactory.INSTANCE.nullStringLookup(),
            prefix: StringMatcher = StringSubstitutor.DEFAULT_PREFIX,
            suffix: StringMatcher = StringSubstitutor.DEFAULT_SUFFIX,
            escape: Char = StringSubstitutor.DEFAULT_ESCAPE,
            valueDelimiter: StringMatcher = StringSubstitutor.DEFAULT_VALUE_DELIMITER): ACTTemplateEngine =
    new ACTTemplateEngine(
      StringSubstitutor(stringLookup, prefix, suffix, escape, valueDelimiter),
      renderer,
      valueFormat)
