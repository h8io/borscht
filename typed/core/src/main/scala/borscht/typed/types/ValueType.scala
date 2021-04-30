package borscht.typed.types

type ValueType = String => AnyRef

trait ValueTypeConstructor extends (List[ValueType] => ValueType):
  protected def validate(typeParameters: List[ValueType]): List[ValueType]

  protected def create(typeParameters: List[ValueType]): ValueType

  override def apply(typeParameters: List[ValueType]): ValueType = create(validate(typeParameters))
