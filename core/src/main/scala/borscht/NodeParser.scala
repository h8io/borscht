package borscht

import scala.annotation.infix

type NodeParser[T] = PartialFunction[Node, T]

val ScalarAnyRefParser: NodeParser[AnyRef] = parsers.ScalarNodeParser andThen (_.unwrapped)

val ScalarStringParser: NodeParser[String] = parsers.ScalarNodeParser andThen (_.asString)
