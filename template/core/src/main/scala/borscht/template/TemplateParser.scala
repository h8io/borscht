package borscht.template

import borscht.Parser

trait TemplateParser extends Parser[Template]:
  def toStringParser: Parser[String] = this andThen (_.render) 
