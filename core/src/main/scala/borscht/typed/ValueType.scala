package borscht.typed

import borscht.NodeParser

trait ValueType:
  def parser(parsers: List[NodeParser[?]]): Either[String, NodeParser[?]]

trait AbstractValueType[T] extends ValueType:
  protected def prepare(parameters: List[NodeParser[?]]): Either[String, T]

  protected def create(parameter: T): NodeParser[?]

  override def parser(parameters: List[NodeParser[?]]): Either[String, NodeParser[?]] = prepare(parameters) map create
