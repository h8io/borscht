package io.h8.cfg.impl.system

import io.h8.cfg.{CfgNode, Node, Position}

class SystemCfgNode(children: Map[String, Node]) extends CfgNode:
  export children.{iterator, get => child}

  override def position: Position = SystemPosition
