package borscht.typed

trait ValueType:
  def parser(parsers: List[ValueParser]): Either[String, ValueParser]

trait AbstractValueType[T] extends ValueType:
  protected def prepare(parameters: List[ValueParser]): Either[String, T]

  protected def create(parameter: T): ValueParser

  override def parser(parameters: List[ValueParser]): Either[String, ValueParser] = prepare(parameters) map create
