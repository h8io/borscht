package borscht.typed.types

import borscht.parsers.{NodeParserRef, NodeParserString}
import borscht.typed.{AbstractRefType, Ref, RefObj}
import borscht.{Node, NodeParserException}

import scala.reflect.ClassTag

object RefTypeAny extends RefTypeParameterless:
  override def apply(node: Node): Ref[Any] = node.as[Ref[Any]]

object RefTypeNode extends RefTypeParameterless:
  override def apply(node: Node): Ref[? <: Node] = RefObj(node)(using ClassTag(node.getClass))

object RefTypeObject extends RefTypeParameterless:
  override def apply(node: Node): RefObj[?] =
    val className = node.as[String]
    try {
      val objectClass = Class.forName(className + "$")
      RefObj(objectClass.getField("MODULE$").get(objectClass))(using ClassTag(objectClass))
    } catch {
      case e: ClassNotFoundException =>
        throw NodeParserException(s"Object $className not found", node.position, e)
      case e: NoSuchFieldException =>
        throw NodeParserException(s"Class $className do not have a companion object", node.position, e)
    }
