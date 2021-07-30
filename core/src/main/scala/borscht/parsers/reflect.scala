package borscht.parsers

import borscht.*
import borscht.reflect.*

import scala.reflect.ClassTag

given NodeParserClass: NodeParser[Class[_]] = NodeParserString andThen Class.forName

given NodeParserComponentRef[T: ClassTag]: NodeParser[ComponentRef[T]] =
  case scalar: ScalarNode =>
    val create = creator(cast[T](NodeParserClass(scalar)))
    ComponentRef(create())
  case cfg: CfgNode =>
    val create = creator(cast[T](cfg[Class[_]]("class")), cfg.child("parameters"))
    ComponentRef(create())
