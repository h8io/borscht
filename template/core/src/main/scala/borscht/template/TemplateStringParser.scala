package borscht.template

import borscht.{Node, Parser, ScalarStringParser}

def TemplateStringParser(parser: Parser[Template]): Parser[String] =
  ScalarStringParser orElse { case node: Node => parser(node).render }
