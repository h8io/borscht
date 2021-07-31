package borscht.typed

import borscht.Node

type ValueParser = PartialFunction[Node, Any]

case class TypedValue(`type`: ValueParser, value: Any)
