package borscht.parsers

import borscht._

given NodeParserScalarNode as NodeParser[ScalarNode] = { case node: ScalarNode => node }

given NodeParserIterableNode as NodeParser[IterableNode] = { case node: IterableNode => node }

given NodeParserObjectNode as NodeParser[ObjectNode] = { case node: ObjectNode => node }
