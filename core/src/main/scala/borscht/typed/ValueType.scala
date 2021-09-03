package borscht.typed

import borscht.NodeParser

import scala.reflect.ClassTag

trait ValueType:
  def parser[T: ClassTag](parsers: List[NodeParser[ValueRef[?]]]): Either[String, NodeParser[ValueRef[T]]]

trait AbstractValueType[P] extends ValueType:
  protected def prepare(parameters: List[NodeParser[ValueRef[?]]]): Either[String, P]

  protected def create[T](parameter: P)(using tag: ClassTag[T]): NodeParser[ValueRef[T]]

  override def parser[T: ClassTag](parameters: List[NodeParser[ValueRef[?]]]): Either[String, NodeParser[ValueRef[T]]] =
    prepare(parameters) map create
