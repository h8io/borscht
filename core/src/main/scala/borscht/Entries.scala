package borscht

final class Entries[T: NodeParser](iterator: Iterator[(String, Node)]) extends Iterator[(String, T)]:
  def this(cfg: CfgNode) = this(cfg.iterator)

  override def hasNext: Boolean = iterator.hasNext

  override def next(): (String, T) =
    val (key, node) = iterator.next
    key -> node.parse[T]
