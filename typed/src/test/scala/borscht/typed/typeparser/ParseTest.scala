package borscht.typed.typeparser

import borscht.typed.ValueType
import borscht.typed.types.{TestValueParser, TestValueType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParseTest extends AnyFlatSpec with Matchers:
  private val types = Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueType(name)).toMap

  "Parse function" should "parse a parameterless definition" in {
    parse("abc", types) shouldEqual Some(TestValueParser("abc", Nil))
  }

  it should "parse definition with parameters" in {
    parse("abc[abc[], def[abc, ghi[jkl]], mno]", types) shouldEqual
      Some(TestValueParser("abc", List(
        TestValueParser("abc", Nil),
        TestValueParser("def", List(
          TestValueParser("abc", Nil),
          TestValueParser("ghi", List(TestValueParser("jkl", Nil))))),
        TestValueParser("mno", Nil))))
  }

  it should "return None on a missed type" in {
    parse("abc[abc[], def[abc, ghi[jkl]], miss]", types) shouldBe None
  }

