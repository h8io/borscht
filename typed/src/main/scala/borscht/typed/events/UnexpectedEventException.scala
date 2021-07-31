package borscht.typed.events

import borscht.typed.ValueTypeParserException

class UnexpectedEventException(val event: Event)
  extends ValueTypeParserException(event.position, s"Unexpected event $event")
