package borscht.parsers

import borscht.*
import borscht.test.*
import borscht.typed.*
import borscht.typed.parser.UnknownTypeException
import borscht.typed.types.{RefTypeParameterless, TestNodeParser, TestRefType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.reflect.ClassTag

class NodeParserNodeParserTest extends AnyFlatSpec with Matchers:
  private object MyRefType extends RefTypeParameterless:
    override def apply(node: Node): Ref[Any] = RefObj(node)(using ClassTag(node.getClass))

  private val testMeta = new Meta(None, Map("my-type" -> MyRefType))

  "Ref parser node" should "parse type correctly" in {
    val meta = Meta(None, Map("type1" -> TestRefType("type1"), "type2" -> TestRefType("type2")))
    scalar("type1[type2, type1[type2, type1]]")
      .withMeta(meta)
      .as[NodeParser[?]](scalar("test")) shouldEqual "type1:[type2:test, type1:[type2:test, type1:test]]"
  }

  it should "throw an exception if it is not defined in meta" in {
    an[UnknownTypeException] should be thrownBy (scalar("my-type").as[NodeParser[?]])
  }

  it should "return a correct value for a scalar value" in {
    (scalar("my-type") withMeta testMeta).as[NodeParser[?]](scalar("test")) shouldEqual scalar("test")
  }
