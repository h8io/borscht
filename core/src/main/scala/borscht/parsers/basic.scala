package borscht.parsers

import borscht.{Node, RenderableString}

import java.lang.{Boolean => jBoolean}
import borscht._

import scala.annotation.tailrec
import scala.collection.mutable
import scala.compiletime.summonFrom
import scala.jdk.CollectionConverters._

given NodeParserBoolean: NodeParser[Boolean] = NodeParserScalarAnyRef andThen {
  case v: jBoolean => v.booleanValue
  case v: String => jBoolean.parseBoolean(v)
  case v: Number => v != 0
}

given NodeParserNumber: NodeParser[Number] = NodeParserScalarAnyRef andThen {
  case v: Number => v
  case v: String => BigDecimal(v)
}

given NodeParserList[T](using parser: NodeParser[T]): NodeParser[List[T]] =
  NodeParserSeqNode andThen { node => (node.iterator map parser).toList }

given NodeParserSet[T] (using parser: NodeParser[T]): NodeParser[Set[T]] =
  NodeParserSeqNode andThen { node => (node.iterator map parser).toSet }

given NodeParserMap[T] (using parser: NodeParser[T]): NodeParser[Map[String, T]] =
  NodeParserCfgNode andThen { cfg =>
    (cfg.iterator map { (key: String, value: Node) => key -> parser(value) }).toMap
  }
