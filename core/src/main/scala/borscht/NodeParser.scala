package borscht

import scala.annotation.implicitNotFound

@implicitNotFound(msg = "No parser available for ${T}")
type NodeParser[T] = Node => T

type PartialNodeParser[T] = PartialFunction[Node, T]

//val NodeParserScalarAny: PartialFunction[Node, Any] = {
//  case scalar: ScalarNode => scalar.value
//}
//
//val NodeParserPlainString: PartialFunction[Node, String] = {
//  case scalar: ScalarNode => scalar.asString
//}

val NodeParserNothing: PartialFunction[Node, Nothing] = PartialFunction.empty[Node, Nothing]
