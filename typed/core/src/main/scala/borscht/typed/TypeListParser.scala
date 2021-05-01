package borscht.typed

import borscht.typed.{ValueType, ValueTypeConstructor}

private[typed] class ValueTypeListParser(parent: UpdatableParser[List[ValueType]],
                                           types: Map[String, ValueTypeConstructor]) extends Parser :
  override def apply(event: Event): Parser = event match
    case Event.TypeListEnd(_) =>
      parent.update(Nil)
      parent(Event.None(event))
    case event: Event.TypeName =>
      val tailParser = new ValueTypeListTailParser(parent, types)
      TypeParser(tailParser, types)(event)
    case unexpected => throw UnexpectedEventException(unexpected)

private class ValueTypeListTailParser(parent: UpdatableParser[List[ValueType]],
                                      types: Map[String, ValueTypeConstructor]) extends UpdatableParser[ValueType]:
  private val builder = List.newBuilder[ValueType]

  override def apply(event: Event): Parser = event match
    case Event.TypeListEnd(_) =>
      parent.update(builder.result)
      parent(Event.None(event))
    case Event.TypeListSeparator(_) => TypeParser(this, types)
    case Event.None(_) => this
    case event => throw UnexpectedEventException(event)

  override def update(value: ValueType): Unit = builder += value
