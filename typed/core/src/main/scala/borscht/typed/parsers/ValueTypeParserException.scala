package borscht.typed.parsers

class ValueTypeParserException(position: Int, message: String, cause: Option[Exception] = None)
  extends RuntimeException(message + "@" + position, cause.orNull)

class UnexpectedEventException(event: Event)
  extends ValueTypeParserException(event.position, s"Unexpected event $event")
