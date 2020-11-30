package borscht

open class BorschtException(message: String, position: Position, cause: Option[Throwable] = None)
  extends RuntimeException(s"$message @ $position", cause.orNull)

class NodeNotFoundException(ref: Iterable[String], position: Position)
  extends BorschtException(s"""Node not found: "${ref mkString "/"}"""", position, None)

open class BorschtNodeParserException(message: String, position: Position, cause: Option[Throwable] = None)
  extends BorschtException(message, position, cause):

  def this(position: Position, cause: Throwable) = this(cause.getMessage, position, Some(cause))
