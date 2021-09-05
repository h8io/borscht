package borscht.typed

import borscht.NodeParser

import scala.reflect.ClassTag

trait RefType:
  def parser(parsers: List[NodeParser[Ref[?]]]): Either[String, NodeParser[Ref[?]]]

trait AbstractValueType[P] extends RefType:
  protected def prepare(parameters: List[NodeParser[Ref[?]]]): Either[String, P]

  protected def create(parameter: P): NodeParser[Ref[?]]

  override def parser(parameters: List[NodeParser[Ref[?]]]): Either[String, NodeParser[Ref[?]]] =
    prepare(parameters) map create
