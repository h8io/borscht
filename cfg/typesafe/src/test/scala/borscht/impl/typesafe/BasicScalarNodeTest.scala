package borscht.impl.typesafe

import borscht.{Recipe, ConfigNode, ScalarNode}
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import scala.language.implicitConversions

class BasicScalarNodeTest extends AnyFlatSpec with Matchers:
  private val recipe = Recipe(provider = TypesafeConfigProvider())
  import recipe.given
  
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
    cfg"key { property1: value1, property2: 2 }"[ConfigNode]("key").iterator.toSeq map { case (key, node) =>
      key -> (node match
        case scalar: ScalarNode => scalar.unwrapped
        case other => fail(s"Invalid node class ${other.getClass}"))
    } must contain theSameElementsAs Map("property1" -> "value1", "property2" -> 2)
  }
