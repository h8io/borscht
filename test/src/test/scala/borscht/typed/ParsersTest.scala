package borscht.typed

import borscht.typed.ValueType
import borscht.typed.parser.UnknownTypeException
import borscht.typed.types.{TestValueParser, TestValueType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParsersTest extends AnyFlatSpec with Matchers:
  private val types = Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueType(name)).toMap

  "Parse type function" should "parse a parameterless definition" in {
    parseType("abc", types) shouldEqual TestValueParser("abc", Nil)
  }

  it should "parse definition with parameters" in {
    parseType("abc[abc[], def[abc, ghi[jkl]], mno]", types) shouldEqual
      TestValueParser("abc", List(
        TestValueParser("abc", Nil),
        TestValueParser("def", List(
          TestValueParser("abc", Nil),
          TestValueParser("ghi", List(TestValueParser("jkl", Nil))))),
        TestValueParser("mno", Nil)))
  }

  it should "return None on a missed type" in {
    val e = the[UnknownTypeException] thrownBy parseType("abc[abc[], def[abc, ghi[jkl]], miss]", types)
    e.`type` shouldBe "miss"
  }

