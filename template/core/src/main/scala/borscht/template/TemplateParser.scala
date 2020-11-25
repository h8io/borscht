package borscht.template

import borscht.{ObjectNodeParser, Parser, ScalarNodeParser, SimpleStringParser}

type TemplateProvider = String => Template

def TemplateParser(provider: TemplateProvider): Parser[Template] =
  SimpleStringParser andThen provider orElse
    (ObjectNodeParser andThen { node =>
      ???
      // provider(node.get[ScalarNode]("template").asString).set(node.map[AnyRef]("parameters"))
    }) 
