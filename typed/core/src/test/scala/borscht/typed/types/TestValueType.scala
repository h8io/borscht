package borscht.typed.types

case class TestValueType(name: String, parameters: List[ValueType]) extends ValueType :
  override def apply(value: String): AnyRef = s"$name:$value"
