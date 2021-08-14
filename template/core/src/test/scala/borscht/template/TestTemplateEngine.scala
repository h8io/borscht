package borscht.template

import borscht.Node
import borscht.parsers.NodeParserString

class TestTemplateEngine(name: String) extends TemplateEngine:
  def this(node: Node) = this(node.parse[String])
  
  override def apply(template: Node): TestTemplate = TestTemplate(name, template.parse[String])
