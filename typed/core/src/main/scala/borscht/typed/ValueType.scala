package borscht.typed

type ValueType = String => AnyRef

trait ValueTypeConstructor extends (List[ValueType] => ValueType):
  protected def validate(typeParameters: List[ValueType]): List[ValueType]

  protected def create(typeParameters: List[ValueType]): ValueType

  override final def apply(typeParameters: List[ValueType]): ValueType = create(validate(typeParameters))
