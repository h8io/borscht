package io.h8.cfg.parsers.default

import io.h8.cfg.{NodeParser, NodeParserPlainString}

given NodeParserString as NodeParser[String] = NodeParserPlainString
