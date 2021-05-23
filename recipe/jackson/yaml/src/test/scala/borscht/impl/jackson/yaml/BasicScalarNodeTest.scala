package borscht.impl.jackson.yaml

import borscht.{CfgNode, ScalarNode}
import borscht.impl.jackson.yaml.YamlRecipe.given
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import scala.language.implicitConversions

class BasicScalarNodeTest extends AnyFlatSpec with Matchers:
  import YamlRecipe.given

  it should "provide a numeric value" in {
    cfg"key: 42"[Number]("key") mustEqual 42
  }

  it should "provide a numeric value from string" in {
    cfg"""key: "42""""[Number]("key") mustEqual 42
  }
  
  it should "provide a nested config" in {
    cfg"""
         |key:
         |  property1: value1
         |  property2: 2"""[CfgNode]("key").iterator.toSeq map { case (key, node) =>
      key -> (node match
        case scalar: ScalarNode => scalar.unwrapped
        case other => fail(s"Invalid node class ${other.getClass}"))
    } must contain theSameElementsAs Map("property1" -> "value1", "property2" -> 2)
  }
