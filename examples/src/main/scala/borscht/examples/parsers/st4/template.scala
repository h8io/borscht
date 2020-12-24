package borscht.examples.parsers.st4

import borscht.NodeParser
import borscht.template.{NodeParserTemplate, NodeParserTemplateParser, Template, TemplateParser}
import borscht.template.impl.st4.ST4TemplateParser

private val st4 = ST4TemplateParser()

given NodeParserST4TemplateParser: NodeParser[TemplateParser] = NodeParserTemplateParser("st4" -> st4)

given NodeParserST4Template: NodeParser[Template] = NodeParserTemplate(st4)
