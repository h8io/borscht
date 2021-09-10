package borscht.parsers

import borscht.*
import borscht.typed.*
import borscht.typedOld
import borscht.typedOld.ValueRef

import scala.reflect.ClassTag

given NodeParserNodeParser: NodeParser[NodeParser[?]] = node => typedOld.parseType(node.as[String], node.meta.valueTypes)

given NodeParserValueRef: NodeParser[ValueRef] = node => ValueRef(typedOld.parseValue(node))

given NodeParserRef[T: ClassTag]: NodeParser[Ref[T]] = node => parseRef(node).cast[T]
