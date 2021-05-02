package borscht.typed

class ValueTypeParserException(position: Int, message: String, cause: Option[Exception] = None)
  extends RuntimeException(message + "@" + position, cause.orNull)
