package borscht.typed.parser

sealed class ValueParserException(message: String, event: Event, cause: Option[Exception] = None)
  extends RuntimeException(s"$message @ ${event.position}", cause.orNull)

final class UnexpectedEventException(event: Event) extends ValueParserException(s"Unexpected event $event", event)

final class ValueParserInstantiationException(message: String, event: Event, cause: Option[Exception] = None)
  extends ValueParserException(s"Value parser instantiation error: $message", event, cause)

final class UnknownTypeException(val `type`: String, event: Event)
  extends ValueParserException(s"Unknown type ${`type`}", event)