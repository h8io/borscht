package borscht.examples.parsers.st4

import borscht.NodeParser
import borscht.template.{createNodeParserTemplate, createNodeParserTemplateParser, Template, TemplateParser}
import borscht.template.impl.st4.ST4TemplateParser

private val st4 = ST4TemplateParser()

given NodeParserTemplateParser: NodeParser[TemplateParser] = createNodeParserTemplateParser("st4" -> st4)

given NodeParserTemplate: NodeParser[Template] = createNodeParserTemplate(st4)
