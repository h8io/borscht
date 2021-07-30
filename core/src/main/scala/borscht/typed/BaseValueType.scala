package borscht.typed

import borscht.*

object BaseValueType extends ValueType:
  override def isDefinedAt(node: Node): Boolean = true

  override def apply(node: Node): Any = node match
    case cfg: CfgNode => cfg
    case seq: SeqNode => seq
    case scalar: ScalarNode => scalar.value

val BaseNodeParserTypedValue: NodeParser[TypedValue] = BaseValueType andThen (TypedValue(BaseValueType, _))
