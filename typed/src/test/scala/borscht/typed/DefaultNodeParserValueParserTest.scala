package borscht.typed

import borscht.test.scalar
import borscht.typed.types.{TestValueParser, TestValueType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DefaultNodeParserValueParserTest extends AnyFlatSpec with Matchers:
  "DefaultNodeParserValueParser" should "parse type correctly" in {
    val parser = DefaultNodeParserValueParser(Map[String, ValueType](
      "type1" -> TestValueType("type1"), "type2" -> TestValueType("type2")))
    parser(scalar("type1[type2, type1[type2, type1]]")) shouldEqual
      TestValueParser("type1", List(
        TestValueParser("type2", Nil),
        TestValueParser("type1", List(
          TestValueParser("type2", Nil),
          TestValueParser("type1", Nil)))))
  }
