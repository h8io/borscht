package borscht.parsers

import borscht.*
import borscht.typed.*
import borscht.typed.types.RefTypeComponent

import scala.reflect.ClassTag

given NodeParserNodeParser: NodeParser[NodeParser[?]] = node =>
  val parser = parseType(node)
  parser(_).value

given NodeParserRef[T: ClassTag]: NodeParser[Ref[T]] = node => parseRef(node).cast[T]

given NodeParserRefComponent[T <: AnyRef : ClassTag]: NodeParser[RefComponent[T]] =
  node => RefTypeComponent(node).assign[T]

