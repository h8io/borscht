package borscht.typed.typeparser

class TypeParserException(event: Event) extends RuntimeException(s"Unexpected event $event @ ${event.position}")
