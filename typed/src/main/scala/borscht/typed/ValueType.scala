package borscht.typed

trait ValueType extends (List[ValueParser] => Either[String, ValueParser])

trait AbstractValueType[T] extends ValueType:
  protected def prepare(parameters: List[ValueParser]): Either[String, T]

  protected def parser(parameter: T): ValueParser

  override def apply(parameters: List[ValueParser]): Either[String, ValueParser] = prepare(parameters) map parser
