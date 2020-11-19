package borscht

open class BorschtException(message: String, position: Position, cause: Option[Throwable] = None)
  extends RuntimeException(s"$message @ $position", cause.orNull)

class PathNotFoundException(path: String, position: Position)
  extends BorschtException(s"Path not found: $path", position, None)

open class BorschtParserException(message: String, position: Position, cause: Option[Throwable] = None)
  extends BorschtException(message, position, cause):

  def this(position: Position, cause: Throwable) = this(cause.getMessage, position, Some(cause))

class UnparsableNodeTypeException(node: Node)
  extends BorschtParserException(s"Unparsable node type ${node.getClass.getSimpleName}", node.position)

class UnparsableValueException(node: ScalarNode)
  extends BorschtParserException(s"Unparsable value $node", node.position)
