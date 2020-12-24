package borscht.examples.parsers.st4

import borscht.NodeParser
import borscht.template.{NodeParserTemplate, SimpleTemplateParserRegistry, Template}
import borscht.template.impl.st4.ST4TemplateParser

given NodeParserST4Template: NodeParser[Template] =
  val st4 = ST4TemplateParser()
  NodeParserTemplate(SimpleTemplateParserRegistry(st4, "st4" -> st4))
