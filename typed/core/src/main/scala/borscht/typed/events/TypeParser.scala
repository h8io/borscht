package borscht.typed.events

import borscht.typed.{ValueType, ValueTypeConstructor}

private[events] class TypeParser(parent: UpdatableParser[ValueType],
                                 types: PartialFunction[String, ValueTypeConstructor]) extends Parser :
  override def apply(event: Event): Parser = event match
    case event: Event.TypeName =>
      val constructor = types.applyOrElse(event.value, _ => throw UnknownType(event))
      ParametersParser(parent, constructor, types)
    case unexpected => throw UnexpectedEvent(unexpected)
