package borscht

import parsers.given

trait ConfigNode(using recipe: Recipe) extends Node with Iterable[(String, Node)]:
  self =>

  def ++(that: ConfigNode): ConfigNode = new ConfigNode with Node:
    def opt[T: NodeParser](path: String): Option[T] = that.opt[T](path) orElse self.opt[T](path)

    def iterator: Iterator[(String, Node)] =
      val primary = that.iterator.toMap
      (self.iterator filterNot { (key, _) => primary.contains(key) }) ++ that.iterator

    def position: Position = self.position | that.position

  final def get[T: NodeParser](path: String): T = opt[T](path) getOrElse (throw PathNotFoundException(path, position))

  def opt[T: NodeParser](path: String): Option[T]

  final def list[T: NodeParser](path: String): List[T] = opt[List[T]](path) getOrElse Nil

  final def set[T: NodeParser](path: String): Set[T] = opt[Set[T]](path) getOrElse Set.empty

  final def map[T: NodeParser](path: String): Map[String, T] = opt[Map[String, T]](path) getOrElse Map.empty
