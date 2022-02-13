package borscht.impl.jackson.toml

import borscht.impl.jackson.toml.TomlRecipe.given
import borscht.parsers.given
import borscht.{CfgNode, ScalarNode}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class BasicScalarNodeTest extends AnyFlatSpec with Matchers:
  "Scalar node parser" should "provide a numeric value" in {
    cfg"key = 42"[BigInt]("key") shouldEqual 42
  }

  it should "provide a numeric value from string" in {
    cfg"""key = "42""""[BigDecimal]("key") shouldEqual 42
  }
  
  it should "provide a nested config" in {
    cfg"""
         |[key]
         |property1 = "value1"
         |property2 = 2"""[CfgNode]("key").iterator.toSeq map { case (key, node) =>
      key -> (node match
        case scalar: ScalarNode => scalar.value
        case other => fail(s"Invalid node class ${other.getClass}"))
    } should contain theSameElementsAs Map("property1" -> "value1", "property2" -> 2)
  }
