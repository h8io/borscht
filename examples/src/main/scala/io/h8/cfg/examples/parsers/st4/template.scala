package io.h8.cfg.examples.parsers.st4

import io.h8.cfg.NodeParser
import io.h8.cfg.template.impl.st4.ST4TemplateParser
import io.h8.cfg.template.{NodeParserTemplate, Template}

private[st4] val NodeParserST4 = NodeParserTemplate(ST4TemplateParser())

given NodeParserTmpl as NodeParser[Template] = NodeParserST4
