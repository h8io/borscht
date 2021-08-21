package borscht.virtual

import borscht.{Meta, Node, Position, ScalarNode}

final case class VirtualScalarNode(value: Any, meta: Meta, position: Position) extends ScalarNode:
  def this(value: Any, node: Node) = this(value, node.meta, node.position)

  override def withMeta(meta: Meta): VirtualScalarNode = copy(meta = meta)
