package borscht.reflect

import borscht.CfgNode

def instantiate[T](subclass: Subclass[T]): T = instantiate(subclass, None)

def instantiate[T](subclass: Subclass[T], optCfg: Option[CfgNode]): T =
  optCfg map (instantiate(subclass, _)) getOrElse {
    try subclass.`class`.getConstructor().newInstance() catch
      case _: NoSuchMethodException => instantiate(subclass, CfgNode.Empty)
  }

def instantiate[T](subclass: Subclass[T], cfg: CfgNode): T =
  subclass.`class`.getConstructor(classOf[CfgNode]).newInstance(cfg)
