package borscht

import borscht.parsers.NodeParserString
import borscht.test.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CfgNodeTest extends AnyFlatSpec with Matchers:
  private val OneOfMap: Map[String, Node => String] = Map(
    "key1" -> (_.as[String] + "-1"),
    "key2" -> (_.as[String] + "-2"))

  "optionalOneOf" should "return None if there are no keys from the map" in {
    cfg("key" -> "value").optionalOneOf(OneOfMap) shouldBe None
  }

  it should "return Some if there is a key from the map" in {
    cfg("key1" -> "value").optionalOneOf(OneOfMap) shouldBe Some("value-1")
  }

  it should "throw a NodeParserException if there are multiple keys from the map" in {
    a[NodeParserException] should be thrownBy cfg("key1" -> "value", "key2" -> "value").optionalOneOf(OneOfMap)
  }

  "oneOf" should "throw a NodeParserException if there are no keys from the map" in {
    a[NodeParserException] should be thrownBy cfg("key" -> "value").oneOf(OneOfMap)
  }

  it should "return a correct value if there is a key from the map" in {
    cfg("key2" -> "value").oneOf(OneOfMap) shouldBe "value-2"
  }

  it should "throw a NodeParserException if there are multiple keys from the map" in {
    a[NodeParserException] should be thrownBy cfg("key1" -> "value", "key2" -> "value").oneOf(OneOfMap)
  }

  "properties" should "return empty map for unexistent node" in {
    cfg().properties("unexistent") shouldBe empty
  }

  it should "return a correct map" in {
    cfg("key1" ->
      cfg("key2" ->
        cfg("cfg" -> cfg(
          "seq" -> seq(1, 2, 3),
          "scalar1" -> "value1",
          "scalar2" -> "value2",
          "empty" -> cfg())))).properties("key1", "key2") shouldEqual Map(
      "key1.key2.cfg.seq.0" -> "1",
      "key1.key2.cfg.seq.1" -> "2",
      "key1.key2.cfg.seq.2" -> "3",
      "key1.key2.cfg.scalar1" -> "value1",
      "key1.key2.cfg.scalar2" -> "value2")
  }
