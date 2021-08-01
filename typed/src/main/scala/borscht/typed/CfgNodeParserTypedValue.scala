package borscht.typed

import borscht.*
import borscht.parsers.given
import borscht.reflect.ComponentRef

class CfgNodeParserTypedValue(val types: Map[String, ValueType]) extends NodeParser[ValueRef]:
  def this(types: CfgNode) = this(types[Map[String, ComponentRef[ValueType]]]() map (_ -> _.get))

  def isDefinedAt(node: Node): Boolean = node match
    case cfg: CfgNode => ???
    case _ => false

  def apply(node: Node): ValueRef = (node: @unchecked) match
    case cfg: CfgNode => ???

  override def orElse[N <: Node, V >: ValueRef](that: PartialFunction[N, V]): PartialFunction[N, V] = that match
    case that: CfgNodeParserTypedValue => CfgNodeParserTypedValue(that.types ++ types)
    case _ => super.orElse(that)
