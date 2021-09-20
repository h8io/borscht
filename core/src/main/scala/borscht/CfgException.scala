package borscht

open class CfgException(message: String, position: Position, cause: Option[Throwable] = None)
  extends RuntimeException(s"$message @ $position", cause.orNull)

class NodeNotFoundException(ref: Iterable[String], position: Position)
  extends CfgException(s"""Node not found: "${ref mkString "/"}"""", position, None)

open class NodeParserException(message: String, position: Position, cause: Option[Throwable] = None)
  extends CfgException(message, position, cause):

  def this(message: String, position: Position, cause: Throwable) = this(message, position, Some(cause))

  def this(position: Position, cause: Throwable) = this(cause.getMessage, position, cause)
