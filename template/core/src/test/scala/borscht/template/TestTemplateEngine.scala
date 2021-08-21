package borscht.template

import borscht.Node
import borscht.parsers.NodeParserString

class TestTemplateEngine(name: String) extends TemplateEngine:
  override def apply(template: Node): TestTemplate = TestTemplate(name, template.parse[String])
