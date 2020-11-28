package borscht.parsers

import borscht._

given NodeParserScalarNode as NodeParser[ScalarNode] = { case node: ScalarNode => node }

given NodeParserIterableNode as NodeParser[IterableNode] = { case node: IterableNode => node }

given NodeParserConfigNode as NodeParser[ConfigNode] = { case node: ConfigNode => node }
