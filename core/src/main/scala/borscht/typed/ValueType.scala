package borscht.typed

import borscht.NodeParser

import scala.reflect.ClassTag

trait ValueType:
  def parser(parsers: List[NodeParser[ValueRef[?]]]): Either[String, NodeParser[ValueRef[?]]]

trait AbstractValueType[P] extends ValueType:
  protected def prepare(parameters: List[NodeParser[ValueRef[?]]]): Either[String, P]

  protected def create(parameter: P): NodeParser[ValueRef[?]]

  override def parser(parameters: List[NodeParser[ValueRef[?]]]): Either[String, NodeParser[ValueRef[?]]] =
    prepare(parameters) map create
