package borscht.typed

class ValueTypeParserException(position: Position, message: String, cause: Option[Exception] = None)
  extends RuntimeException(message + "@" + position, cause.orNull)
