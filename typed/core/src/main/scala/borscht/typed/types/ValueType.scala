package borscht.typed.types

trait ValueType extends (String => AnyRef)

trait ValueTypeConstructor extends ((List[ValueType], Int) => ValueType):
  protected def validate(parameters: List[ValueType], position: Int): List[ValueType]

  protected def create(parameters: List[ValueType]): ValueType

  override final def apply(parameters: List[ValueType], position: Int): ValueType =
    create(validate(parameters, position))
