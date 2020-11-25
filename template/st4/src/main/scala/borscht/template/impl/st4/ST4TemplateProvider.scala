package borscht.template.impl.st4

import borscht.parsers._

import borscht.template.{Template, TemplateProvider}
import org.stringtemplate.v4.ST

final class ST4TemplateProvider extends TemplateProvider:
  override def apply(template: String): Template = ST4Template(ST(template))
