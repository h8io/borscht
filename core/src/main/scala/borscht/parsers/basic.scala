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
  case node => node.meta.nodeParserRenderableString map (_(node).render) getOrElse {
    throw IllegalStateException("Renderable string parser is not defined")
  }

given NodeParserBoolean: NodeParser[Boolean] =
  case scalar: ScalarNode => scalar.value match
    case v: Boolean => v
    case v: jBoolean => v.booleanValue
    case v: String => v.toBoolean
    case v: Number => v != 0
  case node: Node => node.as[String].toBoolean


given NodeParserChar: NodeParser[Char] =
  case scalar: ScalarNode => scalar.value match
    case value: Char => value
    case value: Character => value.charValue
    case value: Int => value.toChar
    case _ => util.parseChar(scalar.asString)
  case node => util.parseChar(node.as[String])


given NodeParserIterator[T](using parser: NodeParser[T]): NodeParser[Iterator[T]] =
  NodeParserSeqNode andThen { node => node.iterator map parser }

given NodeParserList[T: NodeParser]: NodeParser[List[T]] = NodeParserIterator[T] andThen (_.toList)

given NodeParserSet[T] (using parser: NodeParser[T]): NodeParser[Set[T]] =
  NodeParserSeqNode andThen { node => (node.iterator map parser).toSet }

given NodeParserMap[T] (using parser: NodeParser[T]): NodeParser[Map[String, T]] =
  NodeParserCfgNode andThen { cfg =>
    (cfg.iterator map { (key: String, value: Node) => key -> parser(value) }).toMap
  }
