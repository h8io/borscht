package borscht

final class Entries[T: NodeParser](iterator: Iterator[(String, Node)]) extends Iterator[(String, T)]:
  def this(entries: IterableOnce[(String, Node)]) = this(entries.iterator)

  override def hasNext: Boolean = iterator.hasNext

  override def next(): (String, T) =
    val (key, node) = iterator.next
    key -> node.as[T]
