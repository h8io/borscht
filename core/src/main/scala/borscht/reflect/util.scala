package borscht.reflect

import borscht.CfgNode

import scala.reflect.ClassTag

def cast[T](`class`: Class[?])(using tag: ClassTag[T]): Class[? <: T] =
  `class`.asSubclass(tag.runtimeClass.asInstanceOf[Class[T]])

def instantiate[T](`class`: Class[? <: T]): T = instantiate(`class`, None)

def instantiate[T](`class`: Class[? <: T], optCfg: Option[CfgNode]): T =
  optCfg map (instantiate(`class`, _)) getOrElse {
    try `class`.getConstructor().newInstance() catch
      case _: NoSuchMethodException => instantiate(`class`, CfgNode.Empty)
  }

def instantiate[T](`class`: Class[? <: T], cfg: CfgNode): T = `class`.getConstructor(classOf[CfgNode]).newInstance(cfg)
