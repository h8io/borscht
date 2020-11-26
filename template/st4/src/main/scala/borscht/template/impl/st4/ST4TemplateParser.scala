package borscht.template.impl.st4

import borscht.template.{Template, TemplateParser}
import org.stringtemplate.v4.ST

final class ST4TemplateParser extends TemplateParser:
  override def apply(template: String): Template = ST4Template(ST(template))
