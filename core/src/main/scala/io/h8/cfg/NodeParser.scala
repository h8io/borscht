package io.h8.cfg

import io.h8.cfg.Node

type NodeParser[T] = PartialFunction[Node, T]

val NodeParserScalarAnyRef: NodeParser[AnyRef] = parsers.NodeParserScalarNode andThen (_.unwrapped)

val NodeParserPlainString: NodeParser[String] = parsers.NodeParserScalarNode andThen (_.asString)
