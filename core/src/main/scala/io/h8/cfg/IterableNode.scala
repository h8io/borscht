package io.h8.cfg

trait IterableNode(using factory: Factory) extends Node with Iterable[Node]:
  override def toString: String = mkString(s"${getClass.getName}([", ", ", "])")
