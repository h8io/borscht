package borscht

import scala.annotation.infix

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

  @infix
  final def orElse[T](that: Parser[T]): Parser[T] = ???

object ScalarNodeParser extends Parser[ScalarNode]:
  override def apply(node: ScalarNode): ScalarNode = node

object IterableNodeParser extends Parser[IterableNode]:
  override def apply(node: IterableNode): IterableNode = node

object ObjectNodeParser extends Parser[ObjectNode]:
  override def apply(node: ObjectNode): ObjectNode = node

