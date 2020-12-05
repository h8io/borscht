package io.h8.cfg.parsers

import io.h8.cfg._

given NodeParserScalarNode as NodeParser[ScalarNode] = { case node: ScalarNode => node }

given NodeParserIterableNode as NodeParser[IterableNode] = { case node: IterableNode => node }

given NodeParserConfigNode as NodeParser[CfgNode] = { case node: CfgNode => node }
