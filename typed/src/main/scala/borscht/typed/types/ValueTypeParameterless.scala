package borscht.typed.types

import borscht.typed.{AbstractValueType, ValueParser}

trait ValueTypeParameterless extends AbstractValueType[Unit] with ValueParser:
  override protected def prepare(parameters: List[ValueParser]): Either[String, Unit] =
    if (parameters.isEmpty) Right(()) else Left("no parameters expected")

  override protected def create(nothing: Unit): ValueParser = this
