package borscht

trait SeqNode extends Node with Iterable[Node]:
  override def withMeta(meta: Meta): SeqNode
  
  override def toString: String = mkString(s"${getClass.getName}([", ", ", "])")
