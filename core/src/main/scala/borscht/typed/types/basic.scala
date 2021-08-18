package borscht.typed.types

import borscht.{Node, SeqNode, util}
import borscht.parsers.given
import borscht.typed.{AbstractValueType, ValueParser, ValueRef}

import java.lang.Boolean as jBoolean

object ValueTypeString extends ValueTypeInheritedWithStringParser[String]:
  override def parse(value: String): String = value


object ValueTypeBoolean extends ValueTypeInheritedWithStringParser[Boolean]:
  override def parse(value: String): Boolean = value.toBoolean

object ValueTypeJBoolean extends ValueTypeParameterless with StringParser[jBoolean]:
  override def apply(node: Node): jBoolean = node.parse[Boolean]

  override def parse(value: String): jBoolean = value.toBoolean


object ValueTypeChar extends ValueTypeInheritedWithStringParser[Char]:
  override def parse(value: String): Char = util.parseChar(value)

object ValueTypeJChar extends ValueTypeParameterless with StringParser[Character]:
  override def apply(node: Node): Character = node.parse[Char]

  override def parse(value: String): Character = util.parseChar(value)


object ValueTypeList extends ValueTypeWithOptionalParameter:
  override protected def create(parser: Option[ValueParser]): ValueParser = node => node match
    case seq: SeqNode => (parser map seq.iterator.map getOrElse (seq.parse[List[ValueRef]].iterator map (_.get))).toList
    case _ => List(parser map (_(node)) getOrElse node.parse[ValueRef].get)
