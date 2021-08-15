package borscht.typed

import borscht.{CfgNode, Meta, ScalarNode}
import borscht.parsers.{NodeParserMap, NodeParserValueRef}
import borscht.test.{cfg, scalar}
import borscht.typed.types.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DefaultNodeParserValueRefTest extends AnyFlatSpec with Matchers:
  "DefaultNodeParserValueRef" should "be defined on a node with \"type\" and \"value\" children" in {
    DefaultNodeParserValueRef().isDefinedAt(cfg("type" -> "???", "value" -> "???")) shouldBe true
  }

  private def meta = Meta(
    None,
    Some(DefaultNodeParserValueParser(Map(
      "str" -> ValueTypeString(),
      "int" -> ValueTypeInt(),
      "long" -> ValueTypeLong(),
      "bigint" -> ValueTypeBigInt(),
      "prop" -> ValueTypeProp()))),
    Some(DefaultNodeParserValueRef()))

  it should "provide correct values references from cfg nodes" in {
    System.setProperty("prayer", "Cthulhu fhtagn")
    System.setProperty("city", "R'lyeh")
    System.setProperty("factorial", "3628800")
    System.setProperty("secret-ref", "secret-answer")
    System.setProperty("secret-answer", "42")
    val config = cfg(
      "str" -> cfg("type" -> "str", "value" -> "String with \"str\" type"),
      "int" -> cfg("type" -> "int", "value" -> 42),
      "untyped-property" -> cfg("type" -> "prop", "value" -> "prayer"),
      "string-property" -> cfg("type" -> "prop[str]", "value" -> "city"),
      "bigint-property" -> cfg("type" -> "prop[bigint]", "value" -> "factorial"),
      "secret-property" -> cfg("type" -> "prop[prop[long]]", "value" -> "secret-ref")).withMeta(meta)
    config[Map[String, ValueRef]]() map { (key: String, value: ValueRef) => key -> value.value } shouldEqual Map(
      "str" -> "String with \"str\" type",
      "int" -> 42,
      "untyped-property" -> "Cthulhu fhtagn",
      "string-property" -> "R'lyeh",
      "bigint-property" -> BigInt(3628800),
      "secret-property" -> 42L)
  }

  it should "provide correct values references from scalar nodes" in {
    System.setProperty("prayer", "Cthulhu fhtagn")
    System.setProperty("city", "R'lyeh")
    System.setProperty("factorial", "3628800")
    System.setProperty("secret-ref", "secret-answer")
    System.setProperty("secret-answer", "42")
    val config = cfg(
      "str" -> scalar("str:String with \"str\" type"),
      "int" -> scalar("int:42"),
      "untyped-property" -> scalar("prop:prayer"),
      "string-property" -> scalar("prop[str]:city"),
      "bigint-property" -> scalar("prop[bigint]:factorial"),
      "secret-property" -> scalar("prop[prop[long]]:secret-ref")).withMeta(meta)
    config[Map[String, ValueRef]]() map { (key: String, value: ValueRef) => key -> value.value } shouldEqual Map(
      "str" -> "String with \"str\" type",
      "int" -> 42,
      "untyped-property" -> "Cthulhu fhtagn",
      "string-property" -> "R'lyeh",
      "bigint-property" -> BigInt(3628800),
      "secret-property" -> 42L)
  }

  it should "provide correct typeless values references" in {
    cfg("what" -> "answer", "value" -> 42, "is" -> true).withMeta(meta)[Map[String, ValueRef]]() shouldEqual
      Map("what" -> ValueRef("answer"), "value" -> ValueRef(42), "is" -> ValueRef(true))
  }
