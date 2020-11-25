package borscht

import scala.annotation.infix

type Parser[T] = PartialFunction[Node, T]

val ScalarAnyRefParser: Parser[AnyRef] = parsers.ScalarNodeParser andThen (_.unwrapped)

val ScalarStringParser: Parser[String] = parsers.ScalarNodeParser andThen (_.asString)
