package borscht.typed.parsers

import borscht.typed.{ValueType, ValueTypeConstructor}

private[parsers] class ValueTypeParser(parent: UpdatableParser[ValueType],
                                       types: Map[String, ValueTypeConstructor]) extends Parser :
  override def apply(event: Event): Parser = event match
    case Event.TypeName(name, position) =>
      val constructor = types.get(name) getOrElse {
        throw ValueTypeParserException(position, s"Unknown value type $name")
      }
      ValueTypeParametersParser(parent, constructor, types)
    case unexpected => throw UnexpectedEventException(unexpected)
