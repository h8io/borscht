package borscht.typed

import borscht.typed.parsers.Parser
import borscht.typed.{ValueType, ValueTypeConstructor}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RootTypeParserTest extends AnyFlatSpec with Matchers {
  private val types: Map[String, ValueTypeConstructor] =
    Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueTypeConstructor(name)).toMap

  "Value type parser" should "parse a parameterless definition" in {
    ValueTypeParser(types)("abc") shouldEqual TestValueType("abc", Nil)
  }

  it should "parse definition with parameters" in {
    ValueTypeParser(types)("abc[abc[], def[abc, ghi[jkl]], mno]") shouldEqual
      TestValueType("abc", List(
        TestValueType("abc", Nil),
        TestValueType("def", List(
          TestValueType("abc", Nil),
          TestValueType("ghi", List(TestValueType("jkl", Nil))))),
        TestValueType("mno", Nil)))
  }
}
