package io.h8.cfg.impl.system

import io.h8.cfg.CfgNode
import io.h8.cfg.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class SystemCfgNodeTest extends AnyFlatSpec with Matchers:
  "System properies config" should "contain defined properties" in {
    System.setProperty("io.h8.cfg.test.system-cfg-node.property1", "value1")
    System.setProperty("io.h8.cfg.test.system-cfg-node.property2", "value2")
    val cfg = SystemCfgNode()[CfgNode]("io", "h8", "cfg", "test", "system-cfg-node")
    cfg[String]("property1") mustEqual "value1"
    cfg[String]("property2") mustEqual "value2"
  }
