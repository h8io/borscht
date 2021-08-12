package borscht.parsers

import borscht.*
import borscht.test.*
import borscht.typed.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueParserNodeTest extends AnyFlatSpec with Matchers:
  private val config = cfg("parser" -> "my-parser")

  private case class TestValueParser(name: String) extends ValueParser:
    override def apply(node: Node): Any = node

  private object TestNodeParserValueParser extends NodeParser[ValueParser]:
    override def isDefinedAt(node: Node): Boolean = node match
      case scalar: ScalarNode => scalar.value.isInstanceOf[String]
      case _ => false

    override def apply(node: Node): ValueParser = (node: @unchecked) match
      case scalar: ScalarNode => (scalar.value: @unchecked) match
        case value: String => TestValueParser(value)

  private val testMeta = new Meta(None, Some(TestNodeParserValueParser), None)

  "Value parser node parser" should "throw an exception if it is not defined in meta" in {
    a[CfgNodeParserException] should be thrownBy config[ValueParser]("parser")
  }

  it should "return a correct value for a scalar value" in {
    (config withMeta testMeta)[ValueParser]("parser") shouldEqual TestValueParser("my-parser")
  }
