package borscht.template.impl.st4

import borscht.parsers._

import borscht.{ObjectNode, ScalarNode}
import borscht.template.{Template, TemplateParser}
import org.stringtemplate.v4.ST

final class ST4TemplateParser extends TemplateParser {
  override def apply(node: ScalarNode): Template = ST4Template(ST(node.asString))

  override def apply(node: ObjectNode): Template = ???
}
