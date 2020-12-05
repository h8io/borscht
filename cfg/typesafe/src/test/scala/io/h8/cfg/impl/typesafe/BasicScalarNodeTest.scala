package io.h8.cfg.impl.typesafe

import io.h8.cfg.parsers.given
import io.h8.cfg.{CfgNode, Factory, ScalarNode}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import scala.language.implicitConversions

class BasicScalarNodeTest extends AnyFlatSpec with Matchers:
  private val factory = TypesafeFactory()
  import factory.given
  
  "Config" should "provide a string value" in {
    cfg"key: value"[String]("key") mustEqual "value"
  }

  it should "provide a numeric value" in {
    cfg"key: 42"[Number]("key") mustEqual 42
  }

  it should "provide a numeric value from string" in {
    cfg"""key: "42""""[Number]("key") mustEqual 42
  }
  
  it should "provide a nested config" in {
    cfg"key { property1: value1, property2: 2 }"[CfgNode]("key").iterator.toSeq map { case (key, node) =>
      key -> (node match
        case scalar: ScalarNode => scalar.unwrapped
        case other => fail(s"Invalid node class ${other.getClass}"))
    } must contain theSameElementsAs Map("property1" -> "value1", "property2" -> 2)
  }
