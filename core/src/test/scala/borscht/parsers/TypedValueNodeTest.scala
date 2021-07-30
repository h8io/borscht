package borscht.parsers

import borscht.*
import borscht.test.*
import borscht.typed.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class TypedValueNodeTest extends AnyFlatSpec with Matchers:
  private val config = cfg(
    "str" -> "The Answer",
    "num" -> 42,
    "bool" -> true,
    "seq" -> seq(1, 2, 3),
    "cfg" -> cfg("key" -> "value"))

  private object TestValueType extends ValueType:
    override def isDefinedAt(node: Node): Boolean = node match
      case node: ScalarNode => node.value.isInstanceOf[String]
      case _ => false

    override def apply(node: Node): Any = node match
      case scalar: ScalarNode if scalar.value.isInstanceOf[String] => scalar.value.toString + "!"
      case _ => throw MatchError(node)

  private val testMeta = new Meta(None, Some(NodeParserScalarNode andThen TestValueType andThen { value =>
    TypedValue(TestValueType, value)
  }))

  "Scalar typed value node parser" should "return a correct value for the base value type parser" in {
    config[TypedValue]("str") shouldEqual TypedValue(BaseValueType, "The Answer")
    config[TypedValue]("num") shouldEqual TypedValue(BaseValueType, 42)
    config[TypedValue]("bool") shouldEqual TypedValue(BaseValueType, true)
  }

  it should "return a correct value for the test value type parser" in {
    val cfgWithMeta = config withMeta testMeta
    cfgWithMeta[TypedValue]("str") shouldEqual TypedValue(TestValueType, "The Answer!")
    cfgWithMeta[TypedValue]("num") shouldEqual TypedValue(BaseValueType, 42)
    cfgWithMeta[TypedValue]("bool") shouldEqual TypedValue(BaseValueType, true)
  }

  "Sequence typed value node parser" should "return a correct value for the base value type parser" in {
    val tv = config[TypedValue]("seq")
    tv.`type` shouldBe BaseValueType
    tv.value match
      case value: SeqNode => (value.iterator map {
        case node: ScalarNode => node.value
        case node => node
      }).toList should contain theSameElementsInOrderAs List(1, 2, 3)
      case _ => fail(s"Invalid node type: ${tv.`type`} (seq expected)")
  }

  "Configuration typed value node parser" should "return a correct value for the base value type parser" in {
    val tv = config[TypedValue]("cfg")
    tv.`type` shouldBe BaseValueType
    tv.value match
      case value: CfgNode => (value.iterator map {
        case (key: String, node: ScalarNode) => key -> node.value
        case entry => entry
      }).toMap shouldEqual Map("key" -> "value")
      case _ => fail(s"Invalid node type: ${tv.`type`} (cfg expected)")
  }
