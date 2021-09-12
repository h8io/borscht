package borscht.typed.types

import borscht.NodeParser
import borscht.typed.{AbstractRefType, Ref}

trait RefTypeWithOptionalParameter extends AbstractRefType[NodeParser[Ref[?]]]:
  override protected def prepare(parameters: List[NodeParser[Ref[?]]]): Either[String, NodeParser[Ref[?]]] =
    parameters match
      case Nil => Right(RefTypeAny)
      case first :: Nil => Right(first)
      case _ => Left(s"an optional parameter (single or none) expected, but ${parameters.length} received")
  
