package borscht.template.impl.st4

import borscht.NodeParser
import borscht.template.{NodeParserTemplate, NodeParserTemplateString, TemplateParameterValueParser}

def NodeParserST4TemplateString(parameterParser: TemplateParameterValueParser = TemplateParameterValueParser()): NodeParser[String] = 
  NodeParserTemplateString(NodeParserTemplate(ST4TemplateParser(), parameterParser))
