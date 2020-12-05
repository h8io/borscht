package io.h8.cfg.examples.typesafe

import io.h8.cfg.parsers.given
import io.h8.cfg.{CfgNode, Factory, ScalarNode}
import io.h8.cfg.impl.typesafe.TypesafeFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class GivenFactoryTest extends AnyFlatSpec with Matchers:
  "StringParser" should "be usable without given factory" in {
    val factory = TypesafeFactory()
    import factory.given

    val config = cfg"scalar: scalar-value, list: [item1, item2], object { property1: value1, property2: value2 }"
    config[String]("scalar") mustEqual "scalar-value"
  }

  private def testList(cfg: CfgNode) =
    import cfg.given
    cfg[List[String]]("list") must contain theSameElementsInOrderAs List("item1", "item2")

  private def testObject(cfg: CfgNode) =
    import cfg.given
    cfg[CfgNode]("object") map {
      case (key, node: ScalarNode) => key -> node.asString
      case (key, node) => fail(s"Unexpected node type: ($key, $node)")
    } must contain theSameElementsAs Map("property1" -> "value1", "property2" -> "value2")
