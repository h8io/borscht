package borscht.test

import borscht._

private[test] class TestCfgNode(value: Map[String, Node],
                                val meta: Meta = Meta.Empty) extends CfgNode with TestNode(value):
  override def withMeta(meta: Meta): CfgNode =
    TestCfgNode(value map { case (key, node) => key -> node.withMeta(meta) }, meta)

  override def child(key: String): Option[Node] = value.get(key)

  override def iterator: Iterator[(String, Node)] = value.iterator


