package borscht.parsers

import borscht.*
import borscht.test.*
import borscht.typed.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueRefNodeTest extends AnyFlatSpec with Matchers:
  private val config = cfg(
    "str" -> "The Answer",
    "num" -> 42,
    "bool" -> true,
    "seq" -> seq(1, 2, 3),
    "cfg" -> cfg("key" -> "value"))

  private object TestNodeParserValueRef extends NodeParser[ValueRef]:
    override def isDefinedAt(node: Node): Boolean = node match
      case scalar: ScalarNode => scalar.value.isInstanceOf[String]
      case _ => false

    override def apply(node: Node): ValueRef = (node: @unchecked) match
      case scalar: ScalarNode => (scalar.value: @unchecked) match
        case value: String => ValueRef(value + "!")

  private val testMeta = new Meta(None, None, Some(TestNodeParserValueRef))

  "Scalar typed value node parser" should "return a correct value for the base value type parser" in {
    config[ValueRef]("str") shouldEqual ValueRef("The Answer")
    config[ValueRef]("num") shouldEqual ValueRef(42)
    config[ValueRef]("bool") shouldEqual ValueRef(true)
  }

  it should "return a correct value for the test value type parser" in {
    val cfgWithMeta = config withMeta testMeta
    cfgWithMeta[ValueRef]("str") shouldEqual ValueRef("The Answer!")
    cfgWithMeta[ValueRef]("num") shouldEqual ValueRef(42)
    cfgWithMeta[ValueRef]("bool") shouldEqual ValueRef(true)
  }

  "Sequence typed value node parser" should "return a correct value for the base value type parser" in {
    val tv = config[ValueRef]("seq")
    tv.value match
      case value: SeqNode => (value.iterator map {
        case node: ScalarNode => node.value
        case node => node
      }).toList should contain theSameElementsInOrderAs List(1, 2, 3)
      case value: Node => fail(s"Invalid node type: ${value.`type`} (seq expected)")
  }

  "Configuration typed value node parser" should "return a correct value for the base value type parser" in {
    val tv = config[ValueRef]("cfg")
    tv.value match
      case value: CfgNode => (value.iterator map {
        case (key: String, node: ScalarNode) => key -> node.value
        case entry => entry
      }).toMap shouldEqual Map("key" -> "value")
      case value: Node => fail(s"Invalid node type: ${value.`type`} (cfg expected)")
  }
