package borscht.typed

import borscht.typed.{ValueType, ValueTypeConstructor}

private[typed] class TypeParser(parent: UpdatableParser[ValueType],
                                types: Map[String, ValueTypeConstructor]) extends Parser :
  override def apply(event: Event): Parser = event match
    case Event.TypeName(name, position) =>
      val constructor = types.get(name) getOrElse {
        throw ValueTypeParserException(position, s"Unknown value type $name")
      }
      ParametersParser(parent, constructor, types)
    case unexpected => throw UnexpectedEventException(unexpected)
