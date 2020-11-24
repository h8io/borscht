package borscht

import parsers.given

trait ObjectNode(using recipe: Recipe) extends Node with Iterable[(String, Node)]:
  override final def parse[T](using parser: Parser[T]): T = parser(this)

  def +(cfg: ObjectNode): ObjectNode = ???

  final def get[T: Parser](path: String): T = opt[T](path) getOrElse (throw PathNotFoundException(path, position))

  def opt[T: Parser](path: String): Option[T]

  final def list[T: Parser](path: String): List[T] = opt[List[T]](path) getOrElse Nil

  final def set[T: Parser](path: String): Set[T] = opt[Set[T]](path) getOrElse Set.empty

  final def map[T: Parser](path: String): Map[String, T] = opt[Map[String, T]](path) getOrElse Map.empty
