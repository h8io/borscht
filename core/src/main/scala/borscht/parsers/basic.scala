package borscht.parsers

import borscht.{Entries, Node, RenderableString}

import java.lang.{Boolean => jBoolean}
import borscht.*

import scala.annotation.tailrec
import scala.collection.mutable
import scala.compiletime.summonFrom
import scala.jdk.CollectionConverters.*

given NodeParserString: NodeParser[String] =
  case scalar: ScalarNode => scalar.asString
  case node =>
  node.meta.nodeParserRenderableString map (_ (node).render) getOrElse {
    throw IllegalStateException("Renderable string parser is not defined")
  }

given NodeParserBoolean: NodeParser[Boolean] =
  case scalar: ScalarNode =>
  scalar.value match
    case v: Boolean => v
    case v: String => v.toBoolean
    case v: Number => v != 0
  case node: Node => node.as[String].toBoolean

private val EncodedCharPattern = raw"\\u([\d+A-Fa-f]{4})".r

private def parseChar(value: String): Char =
  if (value.length == 1) value.head else value match
    case EncodedCharPattern(code) => Integer.parseInt(code, 16).toChar
    case _ => throw IllegalArgumentException("Could not parse \"$value\" to a character")

given NodeParserChar: NodeParser[Char] =
  case scalar: ScalarNode =>
  scalar.value match
    case value: Char => value
    case value: Int => value.toChar
    case _ => parseChar(scalar.asString)
  case node => parseChar(node.as[String])


given NodeParserIterator[T](using parser: NodeParser[T]): NodeParser[Iterator[T]] =
  NodeParserSeqNode andThen { node => node.iterator map parser }

given NodeParserList[T: NodeParser]: NodeParser[List[T]] = NodeParserIterator[T] andThen (_.toList)

given NodeParserSet[T](using parser: NodeParser[T]): NodeParser[Set[T]] =
  NodeParserSeqNode andThen { node => (node.iterator map parser).toSet }

given NodeParserMap[T](using parser: NodeParser[T]): NodeParser[Map[String, T]] =
  NodeParserCfgNode andThen { cfg =>
    (cfg.iterator map { (key: String, value: Node) => key -> parser(value) }).toMap
  }

given NodeParserOption[T](using parser: NodeParser[T]): NodeParser[Option[T]] = node => node match
  case seq: SeqNode => seq.option[T]
  case _ => Some(parser(node))
