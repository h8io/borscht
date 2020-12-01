package borscht

type NodeParser[T] = PartialFunction[Node, T]

val NodeParserScalarAnyRef: NodeParser[AnyRef] = parsers.NodeParserScalarNode andThen (_.unwrapped)

val NodeParserScalarString: NodeParser[String] = parsers.NodeParserScalarNode andThen (_.asString)
