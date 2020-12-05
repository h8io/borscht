package io.h8.cfg.template

import io.h8.cfg._
import io.h8.cfg.template.{Template, TemplateParameterValueParser}

def NodeParserTemplateString(parser: NodeParser[Template]): NodeParser[String] =
  NodeParserPlainString orElse { case node: Node => parser(node).render }

def NodeParserTemplateString(parser: TemplateParser,
                             parameterParser: TemplateParameterValueParser = TemplateParameterValueParser()): NodeParser[String] =
  NodeParserTemplateString(NodeParserTemplate(parser, parameterParser))
