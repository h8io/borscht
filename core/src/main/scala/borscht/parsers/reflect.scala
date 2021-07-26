package borscht.parsers

import borscht._
import borscht.reflect._

import scala.reflect.ClassTag

given NodeParserClass: NodeParser[Class[_]] = NodeParserString andThen Class.forName

given NodeParserComponent[T: ClassTag]: NodeParser[Component[T]] =
  case scalar: ScalarNode => Component(instantiate(cast[T](NodeParserClass(scalar))))
  case cfg: CfgNode => Component(instantiate(cast[T](cfg[Class[_]]("class")), cfg.get[CfgNode]("cfg")))
