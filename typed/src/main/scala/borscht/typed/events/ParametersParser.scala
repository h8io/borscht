package borscht.typed.events

import borscht.typed.{ValueParser, ValueType}

private[events] final class ParametersParser(parent: UpdatableParser[ValueParser],
                                             `type`: ValueType,
                                             types: PartialFunction[String, ValueType])
  extends UpdatableParser[List[ValueParser]] :

  private var result: List[ValueParser] = Nil

  override def apply(event: Event): Parser = event match
    case Event.TypeListStart(_) => ValueTypeListParser(this, types)
    case event: (Event.None | Event.TypeListSeparator | Event.TypeListEnd | Event.End) =>
      parent.update(`type`(result))
      parent(event)
    case event => throw UnexpectedEvent(event)

  override def update(value: List[ValueParser]): Unit = result = value

private class ValueTypeListParser(parent: UpdatableParser[List[ValueParser]],
                                  types: PartialFunction[String, ValueType]) extends Parser:
  override def apply(event: Event): Parser = event match
    case Event.TypeListEnd(_) =>
      parent.update(Nil)
      parent(Event.None(event))
    case event: Event.TypeName =>
      val tailParser = new ValueTypeListTailParser(parent, types)
      TypeParser(tailParser, types)(event)
    case unexpected => throw UnexpectedEvent(unexpected)

private class ValueTypeListTailParser(parent: UpdatableParser[List[ValueParser]],
                                      types: PartialFunction[String, ValueType]) extends UpdatableParser[ValueParser]:
  private val builder = List.newBuilder[ValueParser]

  override def apply(event: Event): Parser = event match
    case Event.TypeListEnd(_) =>
      parent.update(builder.result)
      parent(Event.None(event))
    case Event.TypeListSeparator(_) => TypeParser(this, types)
    case _: Event.None => this
    case event => throw UnexpectedEvent(event)

  override def update(value: ValueParser): Unit = builder += value
