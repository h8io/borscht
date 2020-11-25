package borscht.parsers

import borscht.Parser

import scala.reflect.ClassTag

given ParserClass[T](using parser: Parser[String]) as Parser[Class[T]] =
  parser andThen { name => Class.forName(name).asInstanceOf[Class[T]] }
