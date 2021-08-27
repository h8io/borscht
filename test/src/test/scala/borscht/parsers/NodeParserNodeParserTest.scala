package borscht.parsers

import borscht.*
import borscht.test.*
import borscht.typed.*
import borscht.typed.parser.UnknownTypeException
import borscht.typed.types.{TestNodeParser, TestValueType, ValueTypeParameterless}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserNodeParserTest extends AnyFlatSpec with Matchers:
  private val config = cfg("parser" -> "my-type")

  private object MyValueType extends ValueTypeParameterless:
    override def apply(node: Node): Any = node

  private val testMeta = new Meta(None, Map("my-type" -> MyValueType))

  "Value parser node" should "parse type correctly" in {
    val meta = Meta(None, Map("type1" -> TestValueType("type1"), "type2" -> TestValueType("type2")))
    scalar("type1[type2, type1[type2, type1]]").withMeta(meta).as[NodeParser[?]] shouldEqual
      TestNodeParser("type1", List(
        TestNodeParser("type2", Nil),
        TestNodeParser("type1", List(
          TestNodeParser("type2", Nil),
          TestNodeParser("type1", Nil)))))
  }

  it should "throw an exception if it is not defined in meta" in {
    val e = the[NodeParserException] thrownBy config[NodeParser[?]]("parser")
    e.getCause shouldBe a[UnknownTypeException]
  }

  it should "return a correct value for a scalar value" in {
    (config withMeta testMeta)[NodeParser[?]]("parser") shouldEqual MyValueType
  }
