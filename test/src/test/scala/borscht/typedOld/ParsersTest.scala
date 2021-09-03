package borscht.typedOld

import borscht.typedOld.ValueType
import borscht.typedOld.parser.UnknownTypeException
import borscht.typedOld.types.{TestNodeParser, TestValueType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParsersTest extends AnyFlatSpec with Matchers:
  private val types = Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueType(name)).toMap

  "Parse type function" should "parse a parameterless definition" in {
    parseType("abc", types) shouldEqual TestNodeParser("abc", Nil)
  }

  it should "parse definition with parameters" in {
    parseType("abc[abc[], def[abc, ghi[jkl]], mno]", types) shouldEqual
      TestNodeParser("abc", List(
        TestNodeParser("abc", Nil),
        TestNodeParser("def", List(
          TestNodeParser("abc", Nil),
          TestNodeParser("ghi", List(TestNodeParser("jkl", Nil))))),
        TestNodeParser("mno", Nil)))
  }

  it should "return None on a missed type" in {
    val e = the[UnknownTypeException] thrownBy parseType("abc[abc[], def[abc, ghi[jkl]], miss]", types)
    e.`type` shouldBe "miss"
  }

