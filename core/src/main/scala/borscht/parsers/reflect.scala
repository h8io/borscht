package borscht.parsers

import borscht.*
import borscht.reflect.*

import scala.reflect.ClassTag

given NodeParserClass: NodeParser[Class[?]] = node => Class.forName(node.as[String])
