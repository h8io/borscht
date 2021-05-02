package borscht.typed.types

class TestValueTypeConstructor(name: String) extends ValueTypeConstructor :
  override protected def validate(parameters: List[ValueType], position: Int): List[ValueType] = parameters

  override protected def create(parameters: List[ValueType]): ValueType = TestValueType(name, parameters)
