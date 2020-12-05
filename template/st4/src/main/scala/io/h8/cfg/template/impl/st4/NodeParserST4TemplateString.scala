package io.h8.cfg.template.impl.st4

import io.h8.cfg.NodeParser
import io.h8.cfg.template.impl.st4.ST4TemplateParser
import io.h8.cfg.template.{NodeParserTemplate, NodeParserTemplateString, TemplateParameterValueParser}

def NodeParserST4TemplateString(parameterParser: TemplateParameterValueParser = TemplateParameterValueParser()): NodeParser[String] =
  NodeParserTemplateString(NodeParserTemplate(ST4TemplateParser(), parameterParser))
