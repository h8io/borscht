package io.h8.cfg.examples.parsers.st4.default

import io.h8.cfg.NodeParser
import io.h8.cfg.examples.parsers.st4.NodeParserST4
import io.h8.cfg.template.NodeParserTemplateString

private val NodeParserST4String = NodeParserTemplateString(NodeParserST4)

given NodeParserString as NodeParser[String] = NodeParserST4String
