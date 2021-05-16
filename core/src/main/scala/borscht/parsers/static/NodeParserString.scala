package borscht.parsers.static

import borscht.{NodeParser, NodeParserNothing, NodeParserPlainString, RenderableString}

given NodeParserString(using parser: NodeParser[RenderableString] = NodeParserNothing): NodeParser[String] =
  NodeParserPlainString orElse (parser andThen (_()))
