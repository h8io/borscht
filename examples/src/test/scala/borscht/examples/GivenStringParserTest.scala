package borscht.examples

import borscht.{ObjectNode, Recipe, ScalarNode}
import borscht.impl.typesafe.TypesafeConfigProvider
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class GivenStringParserTest extends AnyFlatSpec with Matchers:
  "StringParser" should "be usable without given recipe" in {
    val recipe = Recipe(provider = TypesafeConfigProvider())
    import recipe.given
    
    val config = cfg"scalar: scalar-value, list: [item1, item2], object { property1: value1, property2: value2 }"
    config.get[String]("scalar") shouldEqual "scalar-value"
  }

  private def testList(config: ObjectNode) =
    import config.given
    config.get[List[String]]("list") should contain theSameElementsInOrderAs List("item1", "item2")

  private def testObject(config: ObjectNode) =
    import config.given
    config.get[ObjectNode]("object") map {
      case (key, node: ScalarNode) => key -> node.asString
      case (key, node) => fail(s"Unexpected node type: ($key, $node)")
    } should contain theSameElementsAs Map("property1" -> "value1", "property2" -> "value2")
