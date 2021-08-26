package borscht.typed.types

import borscht.NodeParser
import borscht.typed.AbstractValueType

case class TestValueType(name: String) extends AbstractValueType[List[NodeParser[?]]]:
  override protected def prepare(parameters: List[NodeParser[?]]): Either[String, List[NodeParser[?]]] =
    Right(parameters)

  override protected def create(parameters: List[NodeParser[?]]): NodeParser[?] = TestValueParser(name, parameters)
