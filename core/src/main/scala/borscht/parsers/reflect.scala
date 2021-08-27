package borscht.parsers

import borscht.*
import borscht.reflect.*

import scala.reflect.ClassTag

given NodeParserClass: NodeParser[Class[?]] = node => Class.forName(node.as[String])

given NodeParserComponentRef[T: ClassTag]: PartialNodeParser[ComponentRef[T]] =
  case scalar: ScalarNode =>
    val create = creator(cast[T](NodeParserClass(scalar)))
    ComponentRef(create())
  case cfg: CfgNode =>
    val create = creator(cast[T](cfg[Class[?]]("class")), cfg.child("parameters"))
    ComponentRef(create())
