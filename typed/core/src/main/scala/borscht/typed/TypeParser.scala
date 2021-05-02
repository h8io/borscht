package borscht.typed

import borscht.typed.types.{ValueType, ValueTypeConstructor}

private[typed] class TypeParser(parent: UpdatableParser[ValueType],
                                types: Map[String, ValueTypeConstructor]) extends Parser :
  override def apply(event: Event): Parser = event match
    case event@Event.TypeName(name, position) =>
      val constructor = types.get(name) getOrElse {
        throw UnknownValueType(event)
      }
      ParametersParser(parent, constructor, types)
    case unexpected => throw UnexpectedEvent(unexpected)
