package borscht.typed

import borscht.typed.Position

@FunctionalInterface
trait ValueType extends (String => AnyRef)

trait ValueTypeConstructor extends ((List[ValueType], Position) => ValueType):
  protected def validate(parameters: List[ValueType], position: Position): List[ValueType]

  protected def create(parameters: List[ValueType]): ValueType

  override final def apply(parameters: List[ValueType], position: Position): ValueType =
    create(validate(parameters, position))
