package borscht.typedOld.types

import borscht.NodeParser
import borscht.typedOld.AbstractValueType

trait ValueTypeWithOptionalParameter extends AbstractValueType[NodeParser[?]]:
  override protected def prepare(parameters: List[NodeParser[?]]): Either[String, NodeParser[?]] = parameters match
    case Nil => Right(ValueTypeAny)
    case first :: Nil => Right(first)
    case _ => Left(s"an optional parameter (single or none) expected, but ${parameters.length} received")
  
