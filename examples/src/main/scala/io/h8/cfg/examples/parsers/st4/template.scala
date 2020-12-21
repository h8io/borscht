package io.h8.cfg.examples.parsers.st4

import io.h8.cfg.NodeParser
import io.h8.cfg.template.impl.st4.ST4TemplateParser
import io.h8.cfg.template.{NodeParserTemplate, Template}

given NodeParserST4Template: NodeParser[Template] = NodeParserTemplate(ST4TemplateParser())
