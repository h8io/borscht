package borscht.parsers

import borscht.{Entries, Node, RenderableString}

import java.lang.{Boolean => jBoolean}
import borscht.*

import scala.annotation.tailrec
import scala.collection.mutable
import scala.compiletime.summonFrom
import scala.jdk.CollectionConverters.*

given NodeParserString: NodeParser[String] =
  NodeParserPlainString orElse { case node =>
    node.meta.nodeParserRenderableString map (_(node).render) getOrElse {
      throw IllegalStateException("Renderable string parser is not defined")
    }
  }

given NodeParserBoolean: NodeParser[Boolean] = NodeParserScalarAny andThen {
  case v: jBoolean => v.booleanValue
  case v: String => jBoolean.parseBoolean(v)
  case v: Number => v != 0
}

given NodeParserNumber: NodeParser[Number] = NodeParserScalarAny andThen {
  case v: Number => v
  case v: String => BigDecimal(v)
}

given NodeParserIterator[T](using parser: NodeParser[T]): NodeParser[Iterator[T]] =
  NodeParserSeqNode andThen { node => node.iterator map parser }

given NodeParserList[T: NodeParser]: NodeParser[List[T]] = NodeParserIterator[T] andThen (_.toList)

given NodeParserSet[T] (using parser: NodeParser[T]): NodeParser[Set[T]] =
  NodeParserSeqNode andThen { node => (node.iterator map parser).toSet }

given NodeParserMap[T] (using parser: NodeParser[T]): NodeParser[Map[String, T]] =
  NodeParserCfgNode andThen { cfg =>
    (cfg.iterator map { (key: String, value: Node) => key -> parser(value) }).toMap
  }
