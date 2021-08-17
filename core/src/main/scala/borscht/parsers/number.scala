package borscht.parsers

import borscht.{Node, NodeParser, NodeParserScalarAny, ScalarNode}

given NodeParserBigInt: NodeParser[BigInt] =
  case node: ScalarNode => node.value match
    case v: BigInt => v
    case v: Int => BigInt(v)
    case v: Long => BigInt(v)
    case v: Array[Byte] => BigInt(v)
    case _ => BigInt(node.asString)
  case node: Node => BigInt(node.parse[String])

given NodeParserBigDecimal: NodeParser[BigDecimal] =
  case node: ScalarNode => node.value match
    case v: BigDecimal => v
    case v: BigInt => BigDecimal(v)
    case v: Int => BigDecimal(v)
    case v: Long => BigDecimal(v)
    case v: Double => BigDecimal(v)
    case v: Array[Char] => BigDecimal(v)
    case _ => BigDecimal(node.asString)
  case node: Node => BigDecimal(node.parse[String])

given NodeParserByte: NodeParser[Byte] =
  case node: ScalarNode => node.value match
    case v: Byte => v
    case v: Number => v.byteValue
    case _ => node.asString.toByte
  case node: Node => node.parse[String].toByte

given NodeParserDouble: NodeParser[Double] =
  case node: ScalarNode => node.value match
    case v: Double => v
    case v: Number => v.doubleValue
    case _ => node.asString.toDouble
  case node: Node => node.parse[String].toDouble


given NodeParserFloat: NodeParser[Float] =
  case node: ScalarNode => node.value match
    case v: Float => v
    case v: Number => v.floatValue
    case _ => node.asString.toFloat
  case node: Node => node.parse[String].toFloat

given NodeParserInt: NodeParser[Int] =
  case node: ScalarNode => node.value match
    case v: Int => v
    case v: Number => v.intValue
    case _ => node.asString.toInt
  case node: Node => node.parse[String].toInt

given NodeParserLong: NodeParser[Long] =
  case node: ScalarNode => node.value match
    case v: Long => v
    case v: Number => v.longValue
    case _ => node.asString.toLong
  case node: Node => node.parse[String].toLong

given NodeParserShort: NodeParser[Short] =
  case node: ScalarNode => node.value match
    case v: Short => v
    case v: Number => v.shortValue
    case _ => node.asString.toShort
  case node: Node => node.parse[String].toShort
