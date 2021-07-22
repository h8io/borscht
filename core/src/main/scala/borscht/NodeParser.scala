package borscht

type NodeParser[T] = PartialFunction[Node, T]

val NodeParserScalarAnyRef: NodeParser[AnyRef] = parsers.NodeParserScalarNode andThen (_.value)

val NodeParserPlainString: NodeParser[String] = parsers.NodeParserScalarNode andThen (_.asString)

val NodeParserNothing: NodeParser[Nothing] = PartialFunction.empty[Node, Nothing]
