package borscht.typed.types

import borscht.NodeParser
import borscht.typed.{AbstractRefType, Ref}

case class TestRefType(name: String) extends AbstractRefType[List[NodeParser[Ref[?]]]]:
  override protected def prepare(parameters: List[NodeParser[Ref[?]]]): Either[String, List[NodeParser[Ref[?]]]] =
    Right(parameters)

  override protected def create(parameters: List[NodeParser[Ref[?]]]): NodeParser[Ref[?]] =
    TestNodeParser(name, parameters)
