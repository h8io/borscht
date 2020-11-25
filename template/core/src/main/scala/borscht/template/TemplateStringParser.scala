package borscht.template

import borscht.{Node, Parser, SimpleStringParser}

def TemplateStringParser(parser: Parser[Template]): Parser[String] =
  SimpleStringParser orElse { case node: Node => parser(node).render }
