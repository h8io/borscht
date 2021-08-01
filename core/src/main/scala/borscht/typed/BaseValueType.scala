package borscht.typed

import borscht.*

object BaseValueType extends ValueParser:
  override def apply(node: Node): Any = node match
    case cfg: CfgNode => cfg
    case seq: SeqNode => seq
    case scalar: ScalarNode => scalar.value

val BaseNodeParserValueRef: NodeParser[ValueRef] = PartialFunction.fromFunction(BaseValueType andThen (ValueRef(_)))
