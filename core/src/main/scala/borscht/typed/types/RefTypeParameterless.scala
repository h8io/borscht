package borscht.typed.types

import borscht.NodeParser
import borscht.typed.{AbstractRefType, Ref}

trait RefTypeParameterless extends AbstractRefType[Unit] with NodeParser[Ref[?]]:
  override protected def prepare(parameters: List[NodeParser[Ref[?]]]): Either[String, Unit] =
    if parameters.isEmpty then Right(()) else Left("no parameters expected")

  override protected def create(nothing: Unit): NodeParser[Ref[?]] = this
