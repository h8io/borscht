package borscht.typed.events

import borscht.typed.types.{ValueType, ValueTypeConstructor}

private[events] class TypeParser(parent: UpdatableParser[ValueType],
                                 types: PartialFunction[String, ValueTypeConstructor]) extends Parser :
  override def apply(event: Event): Parser = event match
    case event@Event.TypeName(name, position) =>
      val constructor = types.applyOrElse(name, _ => throw UnknownType(event))
      ParametersParser(parent, constructor, types)
    case unexpected => throw UnexpectedEvent(unexpected)
