package io.h8.cfg.parsers

import java.lang.{Boolean => jBoolean}
import io.h8.cfg._
import io.h8.cfg.{Factory, Node}

import scala.annotation.tailrec
import scala.collection.mutable
import scala.compiletime.summonFrom
import scala.jdk.CollectionConverters._

given NodeParserString(using parser: NodeParser[RenderableString] = NodeParserNothing): NodeParser[String] =
  NodeParserPlainString orElse (parser andThen (_.render))

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
