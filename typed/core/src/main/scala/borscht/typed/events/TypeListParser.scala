package borscht.typed.events

import borscht.typed.types.{ValueType, ValueTypeConstructor}

private[events] class ValueTypeListParser(parent: UpdatableParser[List[ValueType]],
                                          types: PartialFunction[String, ValueTypeConstructor]) extends Parser :
  override def apply(event: Event): Parser = event match
    case Event.TypeListEnd(_) =>
      parent.update(Nil)
      parent(Event.None(event))
    case event: Event.TypeName =>
      val tailParser = new ValueTypeListTailParser(parent, types)
      TypeParser(tailParser, types)(event)
    case unexpected => throw UnexpectedEvent(unexpected)

private class ValueTypeListTailParser(parent: UpdatableParser[List[ValueType]],
                                      types: PartialFunction[String, ValueTypeConstructor])
  extends UpdatableParser[ValueType] :

  private val builder = List.newBuilder[ValueType]

  override def apply(event: Event): Parser = event match
    case Event.TypeListEnd(_) =>
      parent.update(builder.result)
      parent(Event.None(event))
    case Event.TypeListSeparator(_) => TypeParser(this, types)
    case _: Event.None => this
    case event => throw UnexpectedEvent(event)

  override def update(value: ValueType): Unit = builder += value
