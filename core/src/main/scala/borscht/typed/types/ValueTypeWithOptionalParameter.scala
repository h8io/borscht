package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

trait ValueTypeWithOptionalParameter extends AbstractValueType[Option[ValueParser]]:
  override protected def prepare(parameters: List[ValueParser]): Either[String, Option[ValueParser]] = parameters match
    case Nil => Right(None)
    case first :: Nil => Right(Some(first))
    case _ => Left("an optional parameter (single or none) expected")
  
