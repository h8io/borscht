package borscht.template

import borscht.Parser

given ParserString(using parser: TemplateParser) as Parser[String] = parser andThen (_.render)  
