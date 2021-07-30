package borscht.typed

import borscht.Node

type ValueType = PartialFunction[Node, Any]

case class TypedValue(`type`: ValueType, value: Any)
