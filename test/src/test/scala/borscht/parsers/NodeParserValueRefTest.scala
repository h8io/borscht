package borscht.parsers

import borscht.*
import borscht.parsers.{NodeParserMap, NodeParserValueRef}
import borscht.test.*
import borscht.typed.types.*
import borscht.typed.{UnknownValueTypeException, ValueRef}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserValueRefTest extends AnyFlatSpec with Matchers:
  private val config = cfg(
    "str" -> "The Answer",
    "num" -> 42,
    "bool" -> true,
    "my-str" -> "my-type:The Answer",
    "seq" -> cfg("type" -> "node", "value" -> seq(1, 2, 3)),
    "cfg" -> cfg("value" -> cfg("key" -> "value")))

  private val testMeta = new Meta(None, Map("my-type" -> new ValueTypeParameterless:
    override def apply(node: Node): Any = node.parse[String] + "!"
  ))

  System.setProperty("prayer", "Cthulhu fhtagn")
  System.setProperty("city", "R'lyeh")
  System.setProperty("factorial", "3628800")
  System.setProperty("secret-ref", "secret-answer")
  System.setProperty("secret-answer", "42")

  "Value reference node" should "provide correct values references from cfg nodes" in {
    val config = cfg(
      "str" -> cfg("type" -> "str", "value" -> "String with \"str\" type"),
      "int" -> cfg("type" -> "int", "value" -> 42),
      "untyped-property" -> cfg("type" -> "prop", "value" -> "prayer"),
      "string-property" -> cfg("type" -> "prop[str]", "value" -> "city"),
      "bigint-property" -> cfg("type" -> "prop[bigint]", "value" -> "factorial"),
      "secret-property" -> cfg("type" -> "prop[prop[long]]", "value" -> "secret-ref"))
    config[Map[String, ValueRef]]() map { (key: String, value: ValueRef) => key -> value.get } shouldEqual Map(
      "str" -> "String with \"str\" type",
      "int" -> 42,
      "untyped-property" -> "Cthulhu fhtagn",
      "string-property" -> "R'lyeh",
      "bigint-property" -> BigInt(3628800),
      "secret-property" -> 42L)
  }

  it should "provide correct values references from scalar nodes" in {
    val config = cfg(
      "str" -> scalar("str:String with \"str\" type"),
      "int" -> scalar("int:42"),
      "untyped-property" -> scalar("prop:prayer"),
      "string-property" -> scalar("prop[str]:city"),
      "bigint-property" -> scalar("prop[bigint]:factorial"),
      "secret-property" -> scalar("prop[prop[long]]:secret-ref"))
    config[Map[String, ValueRef]]() map { (key: String, value: ValueRef) => key -> value.get } shouldEqual Map(
      "str" -> "String with \"str\" type",
      "int" -> 42,
      "untyped-property" -> "Cthulhu fhtagn",
      "string-property" -> "R'lyeh",
      "bigint-property" -> BigInt(3628800),
      "secret-property" -> 42L)
  }

  it should "provide correct typeless values references" in {
    cfg("what" -> "answer", "value" -> 42, "is" -> true)[Map[String, ValueRef]]() shouldEqual
      Map("what" -> ValueRef("answer"), "value" -> ValueRef(42), "is" -> ValueRef(true))
  }

  "Scalar typed value node parser" should "return a correct value for the base value type parser" in {
    an[UnknownValueTypeException] should be thrownBy config[ValueRef]("my-str")
    config[ValueRef]("str") shouldEqual ValueRef("The Answer")
    config[ValueRef]("num") shouldEqual ValueRef(42)
    config[ValueRef]("bool") shouldEqual ValueRef(true)
  }

  it should "return a correct value for the test value type parser" in {
    val cfgWithMeta = config withMeta testMeta
    cfgWithMeta[ValueRef]("my-str") shouldEqual ValueRef("The Answer!")
    cfgWithMeta[ValueRef]("str") shouldEqual ValueRef("The Answer")
    cfgWithMeta[ValueRef]("num") shouldEqual ValueRef(42)
    cfgWithMeta[ValueRef]("bool") shouldEqual ValueRef(true)
  }

  "Sequence typed value node parser" should "return a correct value for the base value type parser" in {
    val tv = config[ValueRef]("seq")
    tv.get match
      case value: SeqNode => (value.iterator map {
        case node: ScalarNode => node.value
        case node => node
      }).toList should contain theSameElementsInOrderAs List(1, 2, 3)
      case value: Node => fail(s"Invalid node type: ${value.`type`} (seq expected)")
  }

  "Configuration typed value node parser" should "return a correct value for the base value type parser" in {
    val tv = config[ValueRef]("cfg")
    tv.get match
      case value: CfgNode => (value.iterator map {
        case (key: String, node: ScalarNode) => key -> node.value
        case entry => entry
      }).toMap shouldEqual Map("key" -> "value")
      case value: Node => fail(s"Invalid node type: ${value.`type`} (cfg expected)")
  }
