package borscht.parsers

import borscht.NodeParser

import scala.reflect.ClassTag

given NodeParserClass[T](using NodeParser: NodeParser[String]) as NodeParser[Class[T]] =
  NodeParser andThen { name => Class.forName(name).asInstanceOf[Class[T]] }
