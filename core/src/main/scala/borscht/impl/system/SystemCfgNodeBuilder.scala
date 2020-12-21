package borscht.impl.system

import borscht.Node

import scala.collection.{immutable, mutable}

private[system] type SystemCfgNodeValue = SystemCfgNodeBuilder | SystemScalarNode

private[system] class SystemCfgNodeBuilder extends mutable.Builder[(String, SystemCfgNodeValue), SystemCfgNode]:
  private val map: mutable.HashMap[String, SystemCfgNodeValue] = mutable.HashMap.empty
  
  export map.{clear, get}

  override def addOne(elem: (String, SystemCfgNodeValue)): SystemCfgNodeBuilder.this.type =
    map += elem
    this

  override def result: SystemCfgNode = new SystemCfgNode(
    (map foldLeft immutable.HashMap.newBuilder[String, Node]) { (builder, kv) =>
      builder += (kv._1 -> (kv._2 match
        case node: SystemScalarNode => node
        case nested: SystemCfgNodeBuilder => nested.result))
    }.result)

