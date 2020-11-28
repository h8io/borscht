package borscht

open class BorschtException(message: String, position: Position, cause: Option[Throwable] = None)
  extends RuntimeException(s"$message @ $position", cause.orNull)

class PathNotFoundException(path: String, position: Position)
  extends BorschtException(s"Path not found: $path", position, None)

open class BorschtNodeParserException(message: String, position: Position, cause: Option[Throwable] = None)
  extends BorschtException(message, position, cause):

  def this(position: Position, cause: Throwable) = this(cause.getMessage, position, Some(cause))
