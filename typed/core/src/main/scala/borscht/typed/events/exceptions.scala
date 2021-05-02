package borscht.typed.events

import borscht.typed.ValueTypeParserException

class UnexpectedEvent(val event: Event)
  extends ValueTypeParserException(event.position, s"Unexpected event $event")

class UnknownType(val event: Event.TypeName)
  extends ValueTypeParserException(event.position, s"Unknown type ${event.value}")
