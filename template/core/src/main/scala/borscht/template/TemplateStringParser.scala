package borscht.template

import borscht.{Node, NodeParser, ScalarStringParser}

def TemplateStringParser(parser: NodeParser[Template]): NodeParser[String] =
  ScalarStringParser orElse { case node: Node => parser(node).render }
