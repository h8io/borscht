package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

trait ValueTypeParameterless extends AbstractValueType[Unit]:
  override protected def prepare(parameters: List[ValueParser]): Either[String, Unit] =
    if (parameters.isEmpty) Right(()) else Left("no parameters expected")
