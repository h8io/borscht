package borscht.examples.parsers

import borscht.NodeParser
import borscht.template.impl.apache.commons.text.ACTTemplateEngine
import borscht.template.impl.st4.ST4TemplateEngine
import borscht.template.{Template, TemplateEngine, createNodeParserTemplate, createNodeParserTemplateParser}
import borscht.typed.{createNodeParserTypedValue, DefaultValueTypeParser, TypedValue, ValueTypeParser}

private val st4 = ST4TemplateEngine()

given NodeParserTypedValue: NodeParser[TypedValue] =
  createNodeParserTypedValue(using DefaultValueTypeParser)

given NodeParserTemplateParser: NodeParser[TemplateEngine] =
  createNodeParserTemplateParser("st4" -> st4, "act" -> ACTTemplateEngine())

given NodeParserTemplate: NodeParser[Template] = createNodeParserTemplate(st4)
