package borscht

import scala.annotation.infix

type Parser[T] = PartialFunction[Node, T]

val ScalarNodeParser: Parser[ScalarNode] = { case node: ScalarNode => node }

val IterableNodeParser: Parser[IterableNode] = { case node: IterableNode => node }

val ObjectNodeParser: Parser[ObjectNode] = { case node: ObjectNode => node }

val SimpleStringParser: Parser[String] = { case node: ScalarNode => node.asString }
