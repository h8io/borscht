package borscht.parsers

import borscht.*
import borscht.parsers.{NodeParserMap, NodeParserRef}
import borscht.test.*
import borscht.typed.*
import borscht.typed.parser.UnknownTypeException
import borscht.typed.types.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.lang.{Boolean as jBoolean, Integer as jInt}

class NodeParserRefTest extends AnyFlatSpec with Matchers:
  private val config = cfg(
    "str" -> "The Answer",
    "num" -> 42,
    "bool" -> true,
    "my-str" -> "my-type:The Answer",
    "seq" -> cfg("node" -> seq(1, 2, 3)),
    "cfg" -> cfg("node" -> cfg("key" -> "value")))

  private val testMeta = new Meta(None, Map("my-type" -> new RefTypeParameterless:
    override def apply(node: Node): Ref[String] = RefObj(node.as[String] + "!")
  ))

  System.setProperty("prayer", "Cthulhu fhtagn")
  System.setProperty("city", "R'lyeh")
  System.setProperty("factorial", "3628800")
  System.setProperty("secret-ref", "secret-answer")
  System.setProperty("secret-answer", "42")

  "Reference node" should "provide values references from cfg nodes" in {
    val config = cfg(
      "str" -> cfg("str" -> "String with \"str\" type"),
      "int" -> cfg("int" -> 42),
      "untyped-property" -> cfg("prop" -> "prayer"),
      "string-property" -> cfg("prop[str]" -> "city"),
      "bigint-property" -> cfg("prop[bigint]" -> "factorial"),
      "secret-property" -> cfg("prop[prop[long]]" -> "secret-ref"))
    config[Map[String, Ref[Any]]]() map { (key: String, value: Ref[Any]) => key -> value.value } shouldEqual Map(
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
    config[Map[String, Ref[Any]]]() map { (key: String, value: Ref[Any]) => key -> value.value } shouldEqual Map(
      "str" -> "String with \"str\" type",
      "int" -> 42,
      "untyped-property" -> "Cthulhu fhtagn",
      "string-property" -> "R'lyeh",
      "bigint-property" -> BigInt(3628800),
      "secret-property" -> 42L)
  }

  it should "provide correct typeless values references" in {
    cfg("what" -> "answer", "value" -> 42, "is" -> true)[Map[String, Ref[Any]]]() shouldEqual
      Map("what" -> RefObj("answer"), "value" -> RefObj[jInt](42), "is" -> RefObj[jBoolean](true))
  }

  "Scalar typed value node parser" should "return a correct value for the base value type parser" in {
    val e = the[NodeParserException] thrownBy config[Ref[Any]]("my-str")
    e.getCause shouldBe a[UnknownTypeException]
    config[Ref[Any]]("str") shouldEqual RefObj("The Answer")
    config[Ref[Any]]("num") shouldEqual RefObj[jInt](42)
    config[Ref[Any]]("bool") shouldEqual RefObj[jBoolean](true)
  }

  it should "return a correct value for the test value type parser" in {
    val cfgWithMeta = config withMeta testMeta
    cfgWithMeta[Ref[Any]]("my-str") shouldEqual RefObj("The Answer!")
    cfgWithMeta[Ref[Any]]("str") shouldEqual RefObj("The Answer")
    cfgWithMeta[Ref[Any]]("num") shouldEqual RefObj[jInt](42)
    cfgWithMeta[Ref[Any]]("bool") shouldEqual RefObj[jBoolean](true)
  }

  "Sequence typed value node parser" should "return a correct value for the base value type parser" in {
    val ref = config[Ref[Any]]("seq")
    ref.value match
      case value: SeqNode => (value.iterator map {
        case node: ScalarNode => node.value
        case node => node
      }).toList should contain theSameElementsInOrderAs List(1, 2, 3)
      case value: Node => fail(s"Invalid node type: ${value.`type`} (seq expected)")
  }

  "Configuration typed value node parser" should "return a correct value for the base value type parser" in {
    val ref = config[Ref[Any]]("cfg")
    ref.value match
      case value: CfgNode => (value.iterator map {
        case (key: String, node: ScalarNode) => key -> node.value
        case entry => entry
      }).toMap shouldEqual Map("key" -> "value")
      case value: Node => fail(s"Invalid node type: ${value.`type`} (cfg expected)")
  }
