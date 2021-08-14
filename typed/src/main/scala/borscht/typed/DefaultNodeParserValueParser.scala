package borscht.typed

import borscht.*
import borscht.parsers.given
import borscht.reflect.ComponentRef

final class DefaultNodeParserValueParser(val types: Map[String, ValueType]) extends NodeParser[ValueParser]:
  def this(types: CfgNode) = this(types[Map[String, ComponentRef[ValueType]]]() map (_ -> _.get))

  override def isDefinedAt(node: Node): Boolean = node match
    case scalar: ScalarNode => scalar.value match
      case value: String => valueparser.parse(value, types).isDefined
      case _ => false
    case _ => false

  override def apply(node: Node): ValueParser =
    val `type` = node.parse[String]
    valueparser.parse(node.parse[String], types) getOrElse { throw UnknownValueType(`type`, node.position) }

  override def orElse[N <: Node, V >: ValueParser](that: PartialFunction[N, V]): PartialFunction[N, V] = that match
    case that: DefaultNodeParserValueParser => DefaultNodeParserValueParser(that.types ++ types)
    case _ => super.orElse(that)
