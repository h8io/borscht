package borscht.typed

import borscht.*
import borscht.parsers.given
import borscht.reflect.ComponentRef

class CfgNodeParserTypedValue(val types: Map[String, ValueType]) extends NodeParser[TypedValue]:
  def this(types: CfgNode) = this(types[Map[String, ComponentRef[ValueType]]]() map (_ -> _.get))

  def isDefinedAt(node: Node): Boolean = ???

  def apply(node: Node): TypedValue = ???

  override def orElse[N <: Node, V >: TypedValue](that: PartialFunction[N, V]): PartialFunction[N, V] = that match
    case that: CfgNodeParserTypedValue => CfgNodeParserTypedValue(that.types ++ types)
    case _ => super.orElse(that)
