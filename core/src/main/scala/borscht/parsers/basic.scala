package borscht.parsers

import java.lang.{Boolean => jBoolean}

import borscht._

import scala.annotation.tailrec
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

given NodeParserString(using recipe: Recipe) as NodeParser[String] = recipe.stringParser

given NodeParserBoolean as NodeParser[Boolean] = ScalarNodeParser andThen { node => node.unwrapped match
  case v: jBoolean => v
  case v: String => jBoolean.parseBoolean(v)
  case v: Number => v != 0
  case v => throw UnparsableValueException(node)
}

given NodeParserNumber as NodeParser[Number] = ScalarNodeParser andThen { node => node.unwrapped match
  case v: Number => v
  case v: String => BigDecimal(v)
  case v => throw UnparsableValueException(node)
}

given NodeParserList[T](using parser: NodeParser[T]) as NodeParser[List[T]] =
  IterableNodeParser andThen { node => (node.iterator map parser).toList }

given NodeParserSet[T] (using parser: NodeParser[T]) as NodeParser[Set[T]] =
  IterableNodeParser andThen { node => (node.iterator map parser).toSet }

given NodeParserMap[T] (using parser: NodeParser[T]) as NodeParser[Map[String, T]] =
  ObjectNodeParser andThen { node =>
    (node.iterator map { (key: String, value: Node) => key -> parser(value) }).toMap
  }
