package borscht.typed

import borscht.typed.Position

@FunctionalInterface
trait ValueType extends (String => AnyRef)

trait ValueTypeConstructor extends ((List[ValueParser], Position) => ValueParser):
  protected def validate(parameters: List[ValueParser], position: Position): List[ValueParser]

  protected def create(parameters: List[ValueParser]): ValueParser

  override final def apply(parameters: List[ValueParser], position: Position): ValueParser =
    create(validate(parameters, position))
