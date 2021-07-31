package borscht.typed

import borscht.Node

type ValueParser = PartialFunction[Node, Any]

case class ValueRef(value: Any) extends AnyVal
