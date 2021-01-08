package borscht.examples.parsers

import borscht.NodeParser
import borscht.template.impl.apache.commons.text.ACTTemplateParser
import borscht.template.impl.st4.ST4TemplateParser
import borscht.template.{Template, TemplateParser, createNodeParserTemplate, createNodeParserTemplateParser}

private val st4 = ST4TemplateParser()

given NodeParserTemplateParser: NodeParser[TemplateParser] =
  createNodeParserTemplateParser("st4" -> st4, "act" -> ACTTemplateParser())

given NodeParserTemplate: NodeParser[Template] = createNodeParserTemplate(st4)
