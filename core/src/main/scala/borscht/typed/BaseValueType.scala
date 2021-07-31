package borscht.typed

import borscht.*

object BaseValueType extends ValueParser:
  override def isDefinedAt(node: Node): Boolean = true

  override def apply(node: Node): Any = node match
    case cfg: CfgNode => cfg
    case seq: SeqNode => seq
    case scalar: ScalarNode => scalar.value

val BaseNodeParserValueRef: NodeParser[ValueRef] = BaseValueType andThen (ValueRef(_))
