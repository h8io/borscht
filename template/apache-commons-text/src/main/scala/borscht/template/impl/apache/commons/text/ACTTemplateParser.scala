package borscht.template.impl.apache.commons.text

import borscht.template.{Template, TemplateParser}
import org.apache.commons.text.StringSubstitutor
import org.apache.commons.text.lookup.{StringLookup, StringLookupFactory}
import org.apache.commons.text.matcher.StringMatcher

final class ACTTemplateParser(substitutor: StringSubstitutor) extends TemplateParser:
  override def apply(template: String): Template = ACTTemplate(substitutor, template)

object ACTTemplateParser:
  def apply(stringLookup: StringLookup = StringLookupFactory.INSTANCE.nullStringLookup(),
            prefix: StringMatcher = StringSubstitutor.DEFAULT_PREFIX,
            suffix: StringMatcher = StringSubstitutor.DEFAULT_SUFFIX,
            escape: Char = StringSubstitutor.DEFAULT_ESCAPE,
            valueDelimiter: StringMatcher = StringSubstitutor.DEFAULT_VALUE_DELIMITER): ACTTemplateParser =
    new ACTTemplateParser(StringSubstitutor(stringLookup, prefix, suffix, escape, valueDelimiter))
