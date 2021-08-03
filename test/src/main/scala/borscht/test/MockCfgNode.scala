package borscht.test

import borscht.*

private[test] case class MockCfgNode(cfg: Map[String, ?], meta: Meta, position: Position) extends CfgNode:
  override def withMeta(meta: Meta): CfgNode = copy(meta = meta)

  override def iterator: Iterator[(String, Node)] = cfg.iterator map { (key, value) =>
    key -> wrap(value, meta, MockCfgPosition(position, key))
  }

  override def child(key: String): Option[Node] = cfg.get(key) map (wrap(_, meta, MockCfgPosition(position, key)))

private case class MockCfgPosition(position: Position, key: String) extends Position.Some:
  override def toString: String = s"[$position, key $key]"
