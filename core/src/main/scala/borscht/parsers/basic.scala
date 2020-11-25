package borscht.parsers

import java.lang.{Boolean => jBoolean}

import borscht._

import scala.annotation.tailrec
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

given ParserString(using recipe: Recipe) as Parser[String] = recipe.stringParser

given ParserBoolean as Parser[Boolean] = ScalarNodeParser andThen { node => node.unwrapped match
  case v: jBoolean => v
  case v: String => jBoolean.parseBoolean(v)
  case v: Number => v != 0
  case v => throw UnparsableValueException(node)
}

given ParserNumber as Parser[Number] = ScalarNodeParser andThen { node => node.unwrapped match
  case v: Number => v
  case v: String => BigDecimal(v)
  case v => throw UnparsableValueException(node)
}

given ParserList[T](using parser: Parser[T]) as Parser[List[T]] =
  IterableNodeParser andThen { node => (node.iterator map parser).toList }

given ParserSet[T] (using parser: Parser[T]) as Parser[Set[T]] =
  IterableNodeParser andThen { node => (node.iterator map parser).toSet }

given ParserMap[T] (using parser: Parser[T]) as Parser[Map[String, T]] =
  ObjectNodeParser andThen { node => (node.iterator map { (key: String, value: Node) => key -> parser(value) }).toMap }
