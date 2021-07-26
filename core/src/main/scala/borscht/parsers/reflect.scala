package borscht.parsers

import borscht._
import borscht.reflect._

import scala.reflect.ClassTag

given NodeParserClass: NodeParser[Class[_]] = NodeParserString andThen Class.forName

given NodeParserComponent[T: ClassTag]: NodeParser[ComponentRef[T]] =
  case scalar: ScalarNode => ComponentRef(instantiate(cast[T](NodeParserClass(scalar))))
  case cfg: CfgNode => ComponentRef(instantiate(cast[T](cfg[Class[_]]("class")), cfg.get[CfgNode]("cfg")))
