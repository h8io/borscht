package borscht.typed

class ValueTypeParserException(position: Int, message: String, cause: Option[Exception] = None)
  extends RuntimeException(message + "@" + position, cause.orNull)

class UnexpectedEventException(val event: Event)
  extends ValueTypeParserException(event.position, s"Unexpected event $event")
