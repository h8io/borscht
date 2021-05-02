package borscht.typed.events

import borscht.typed.types.{ValueType, ValueTypeConstructor}

private[events] final class ParametersParser(parent: UpdatableParser[ValueType],
                                             constructor: ValueTypeConstructor,
                                             types: PartialFunction[String, ValueTypeConstructor])
  extends UpdatableParser[List[ValueType]] :

  private var result: List[ValueType] = Nil

  override def apply(event: Event): Parser = event match
    case Event.TypeListStart(_) => ValueTypeListParser(this, types)
    case event: (Event.TypeName | ErrorEvent) => throw UnexpectedEvent(event)
    case event =>
      parent.update(constructor(result, event.position))
      parent(event)

  override def update(value: List[ValueType]): Unit = result = value
