package borscht.examples.parsers.st4

import borscht.NodeParser
import borscht.template.{NodeParserTemplate, Template}
import borscht.template.impl.st4.ST4TemplateParser

given NodeParserST4Template: NodeParser[Template] = NodeParserTemplate(ST4TemplateParser())
