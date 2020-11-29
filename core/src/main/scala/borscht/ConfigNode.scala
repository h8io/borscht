package borscht

import parsers.given 

import scala.annotation.infix

trait ConfigNode(using recipe: Recipe) extends Node with Iterable[(String, Node)]:
  @infix
  def ++(that: ConfigNode): ConfigNode = ConfigNode.Merged(this, that)

  final def get[T: NodeParser](path: String): T = opt[T](path) getOrElse (throw PathNotFoundException(path, position))

  final def opt[T](path: String)(using parser: NodeParser[T]): Option[T] = node(path) map { n =>
    try parser(n) catch { case e: Exception => throw BorschtNodeParserException(n.position, e) }
  }

  def node(path: String): Option[Node]

  final def list[T: NodeParser](path: String): List[T] = opt[List[T]](path) getOrElse Nil

  final def set[T: NodeParser](path: String): Set[T] = opt[Set[T]](path) getOrElse Set.empty

  final def map[T: NodeParser](path: String): Map[String, T] = opt[Map[String, T]](path) getOrElse Map.empty

object ConfigNode:
  private def merge(optFallback: Option[Node], optMain: Option[Node]): Option[Node] =
    optMain map { main => optFallback map { fallback =>
      (fallback, main) match {
        case (fallback: ConfigNode, main: ConfigNode) => fallback ++ main
        case _ => main
      }
    } getOrElse main } orElse optFallback
  
  private case class Merged(fallback: ConfigNode, main: ConfigNode)(using recipe: Recipe) extends ConfigNode with Node:
    def node(path: String): Option[Node] = merge(fallback.node(path), main.node(path))

    def iterator: Iterator[(String, Node)] =
      def updated = (fallback.iterator map { (key, node) =>
        key -> merge(Some(node), main.node(key)).get
      }).toMap
      updated.iterator ++ (main.iterator filterNot { (key, _) => updated.contains(key) })

    def position: Position = main.position + fallback.position
