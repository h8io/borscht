package borscht.template

import borscht.{NodeParser, NodeParserPlainString}

def NodeParserTemplateParser(parsers: Map[String, TemplateParser]): NodeParser[TemplateParser] =
  NodeParserPlainString andThen parsers 

def NodeParserTemplateParser(parsers: (String, TemplateParser)*): NodeParser[TemplateParser] =
  NodeParserTemplateParser(parsers.toMap)
