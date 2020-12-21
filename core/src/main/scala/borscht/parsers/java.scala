package borscht.parsers

import borscht.NodeParser

given NodeParserClass[T](using parser: NodeParser[String]): NodeParser[Class[T]] =
  parser andThen { name => Class.forName(name).asInstanceOf[Class[T]] }
