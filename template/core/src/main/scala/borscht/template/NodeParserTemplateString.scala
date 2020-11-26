package borscht.template

import borscht.{Node, NodeParser, NodeParserScalarString}

def NodeParserTemplateString(parser: NodeParser[Template]): NodeParser[String] =
  NodeParserScalarString orElse { case node: Node => parser(node).render }
