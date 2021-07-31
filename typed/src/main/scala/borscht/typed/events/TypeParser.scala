package borscht.typed.events

import borscht.typed.{ValueParser, ValueType}

private[events] class TypeParser(parent: UpdatableParser[ValueParser],
                                 types: PartialFunction[String, ValueType]) extends Parser:
  override def apply(event: Event): Parser = event match
    case event: Event.TypeName =>
      val `type` = types.applyOrElse(event.value, _ => throw UnknownType(event))
      ParametersParser(parent, `type`, types)
    case unexpected => throw UnexpectedEvent(unexpected)
