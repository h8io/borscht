package borscht.parsers

import java.lang.{Boolean => jBoolean}

import borscht.{Recipe, IterableNode, ObjectNode, Parser, ScalarNode, UnparsableValueException}

import scala.annotation.tailrec
import scala.collection.mutable
import scala.jdk.CollectionConverters._
import scala.language.implicitConversions

given ParserString(using recipe: Recipe) as Parser[String] = recipe.stringParser

given ParserBoolean as Parser[Boolean]:
  override def apply(node: ScalarNode): Boolean = node.unwrapped match
    case v: jBoolean => v
    case v: String => jBoolean.parseBoolean(v)
    case v: Number => v != 0
    case v => throw UnparsableValueException(node)

given ParserNumber as Parser[Number]:
  override def apply(node: ScalarNode): Number = node.unwrapped match
    case v: Number => v
    case v: String => BigDecimal(v)
    case v => throw UnparsableValueException(node)

given ParserList[T] (using parser: Parser[T]) as Parser[List[T]]:
  override def apply(node: ScalarNode): List[T] = List(node.parse[T])
  override def apply(node: IterableNode): List[T] = (node.iterator map (_.parse[T])).toList

given ParserSet[T] (using parser: Parser[T]) as Parser[Set[T]]:
  override def apply(node: ScalarNode): Set[T] = Set(node.parse[T])
  override def apply(node: IterableNode): Set[T] = (node.iterator map (_.parse[T])).toSet

given ParserMap[T] (using parser: Parser[T]) as Parser[Map[String, T]]:
  override def apply(node: ObjectNode): Map[String, T] = (node.iterator map (_ -> _.parse[T])).toMap
