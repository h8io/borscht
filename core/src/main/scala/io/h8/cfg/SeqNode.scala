package io.h8.cfg

trait SeqNode extends Node with Iterable[Node]:
  override def toString: String = mkString(s"${getClass.getName}([", ", ", "])")
