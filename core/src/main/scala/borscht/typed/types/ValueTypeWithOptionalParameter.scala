package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

trait ValueTypeWithOptionalParameter extends AbstractValueType[ValueParser]:
  override protected def prepare(parameters: List[ValueParser]): Either[String, ValueParser] = parameters match
    case Nil => Right(ValueTypeAny)
    case first :: Nil => Right(first)
    case _ => Left(s"an optional parameter (single or none) expected, but ${parameters.length} received")
  
