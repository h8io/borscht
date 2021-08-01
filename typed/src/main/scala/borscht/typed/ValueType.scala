package borscht.typed

trait ValueType extends (List[ValueParser] => ValueParser)

trait AbstractValueType extends ValueType:
  protected def prepare(parsers: List[ValueParser]): List[ValueParser]

  protected def parser(parsers: List[ValueParser]): ValueParser

  override def apply(parsers: List[ValueParser]): ValueParser = parser(prepare(parsers))
