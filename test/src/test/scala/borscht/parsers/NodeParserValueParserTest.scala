package borscht.parsers

import borscht.*
import borscht.test.*
import borscht.typed.*
import borscht.typed.types.{TestValueParser, TestValueType, ValueTypeParameterless}
import borscht.typed.valueparser.UnknownTypeException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserValueParserTest extends AnyFlatSpec with Matchers:
  private val config = cfg("parser" -> "my-type")

  private object MyValueType extends ValueTypeParameterless:
    override def apply(node: Node): Any = node

  private val testMeta = new Meta(None, Map("my-type" -> MyValueType))

  "Value parser node" should "parse type correctly" in {
    val meta = Meta(None, Map("type1" -> TestValueType("type1"), "type2" -> TestValueType("type2")))
    scalar("type1[type2, type1[type2, type1]]").withMeta(meta).parse[ValueParser] shouldEqual
      TestValueParser("type1", List(
        TestValueParser("type2", Nil),
        TestValueParser("type1", List(
          TestValueParser("type2", Nil),
          TestValueParser("type1", Nil)))))
  }

  it should "throw an exception if it is not defined in meta" in {
    val e = the[NodeParserException] thrownBy config[ValueParser]("parser")
    e.getCause shouldBe a[UnknownTypeException]
  }

  it should "return a correct value for a scalar value" in {
    (config withMeta testMeta)[ValueParser]("parser") shouldEqual MyValueType
  }
