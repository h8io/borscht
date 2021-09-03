package borscht.typedOld.types

import borscht.NodeParser
import borscht.typedOld.AbstractValueType

trait ValueTypeParameterless extends AbstractValueType[Unit] with NodeParser[?]:
  override protected def prepare(parameters: List[NodeParser[?]]): Either[String, Unit] =
    if (parameters.isEmpty) Right(()) else Left("no parameters expected")

  override protected def create(nothing: Unit): NodeParser[?] = this
