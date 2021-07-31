package borscht.typed

trait ValueType extends (List[ValueParser] => ValueParser)

trait AbstractValueType extends ValueType:
  def prepare(parsers: List[ValueParser]): List[ValueParser]

  def create(parsers: List[ValueParser]): ValueParser

  override def apply(parsers: List[ValueParser]): ValueParser = create(prepare(parsers))
