package borscht.parsers

import borscht.NodeParser
import borscht.reflect.Subclass

import scala.reflect.ClassTag

given NodeParserClass: NodeParser[Class[_]] = NodeParserString andThen Class.forName

given NodeParserSubclass[T: ClassTag]: NodeParser[Subclass[T]] = NodeParserString andThen Subclass[T]
