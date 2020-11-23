package borscht.impl.typesafe

import borscht.{Recipe, ObjectNode, ScalarNode}
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class BasicScalarNodeTest extends AnyFlatSpec with Matchers:
  private val recipe = Recipe(provider = TypesafeConfigProvider())
  import recipe.given
  
  "Config" should "provide a string value" in {
    cfg"key: value".get[String]("key") shouldEqual "value"
  }

  it should "provide a numeric value" in {
    cfg"key: 42".get[Number]("key") shouldEqual 42
  }

  it should "provide a numeric value from string" in {
    cfg"""key: "42"""".get[Number]("key") shouldEqual 42
  }
  
  it should "provide a nested config" in {
    cfg"key { property1: value1, property2: 2 }".get[ObjectNode]("key").iterator.toSeq map { case (key, node) =>
      key -> (node match
        case scalar: ScalarNode => scalar.unwrapped
        case other => fail(s"Invalid node class ${other.getClass}"))
    } should contain theSameElementsAs Map("property1" -> "value1", "property2" -> 2)
  }
