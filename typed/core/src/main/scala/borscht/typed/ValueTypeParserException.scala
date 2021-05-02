package borscht.typed

import borscht.typed.types.ValueType

class ValueTypeParserException(position: Int, message: String, cause: Option[Exception] = None)
  extends RuntimeException(message + "@" + position, cause.orNull)

class UnexpectedEvent(val event: Event)
  extends ValueTypeParserException(event.position, s"Unexpected event $event")

class UnknownValueType(val event: Event.TypeName)
  extends ValueTypeParserException(event.position, s"Unknown value type ${event.value}")
