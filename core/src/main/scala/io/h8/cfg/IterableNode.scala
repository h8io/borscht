package io.h8.cfg

trait IterableNode extends Node with Iterable[Node]:
  override def toString: String = mkString(s"${getClass.getName}([", ", ", "])")
