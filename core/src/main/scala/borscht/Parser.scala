package borscht

import scala.annotation.infix
import scala.jdk.CollectionConverters._

trait Parser[T]:
  self =>

  def apply(node: ScalarNode): T = throw UnparsableNodeTypeException(node)

  def apply(node: IterableNode): T = throw UnparsableNodeTypeException(node)

  def apply(node: ObjectNode): T = throw UnparsableNodeTypeException(node)

  @infix
  final def andThen[U](f: T => U): Parser[U] = new Parser[U]:
    override def apply(node: ScalarNode): U = f(self(node))

    override def apply(node: IterableNode): U = f(self(node))

    override def apply(node: ObjectNode): U = f(self(node))
