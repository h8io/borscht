package borscht

import scala.annotation.implicitNotFound

@implicitNotFound(msg = "No parser available for ${T}")
type NodeParser[T] = Node => T

type PartialNodeParser[T] = PartialFunction[Node, T]

val NodeParserNothing: PartialFunction[Node, Nothing] = PartialFunction.empty[Node, Nothing]
