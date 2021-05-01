package borscht.typed.parsers

import borscht.typed.{ValueType, ValueTypeConstructor}

private[parsers] final class ParametersParser(parent: UpdatableParser[ValueType],
                                              constructor: ValueTypeConstructor,
                                              types: Map[String, ValueTypeConstructor])
  extends UpdatableParser[List[ValueType]] :

  private var result: List[ValueType] = Nil

  override def apply(event: Event): Parser = event match
    case Event.TypeListStart(_) => ValueTypeListParser(this, types)
    case event =>
      parent.update(constructor(result))
      parent(event)

  override def update(value: List[ValueType]): Unit = result = value
