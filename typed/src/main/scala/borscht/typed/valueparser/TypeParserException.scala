package borscht.typed.valueparser

class TypeParserException(event: Event) extends RuntimeException(s"Unexpected event $event @ ${event.position}")
