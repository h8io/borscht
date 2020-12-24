package borscht.template

import borscht.{NodeParser, NodeParserPlainString}

def createNodeParserTemplateParser(parsers: Map[String, TemplateParser]): NodeParser[TemplateParser] =
  NodeParserPlainString andThen parsers 

def createNodeParserTemplateParser(parsers: (String, TemplateParser)*): NodeParser[TemplateParser] =
  createNodeParserTemplateParser(parsers.toMap)
