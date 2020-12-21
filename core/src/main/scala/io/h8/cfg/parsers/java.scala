package io.h8.cfg.parsers

import io.h8.cfg.NodeParser

given NodeParserClass[T](using parser: NodeParser[String]): NodeParser[Class[T]] =
  parser andThen { name => Class.forName(name).asInstanceOf[Class[T]] }
