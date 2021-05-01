package borscht.typed.parsers

import borscht.typed.{ValueType, ValueTypeConstructor}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueTypeParserTest extends AnyFlatSpec with Matchers {
  private val types: Map[String, ValueTypeConstructor] =
    Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueTypeConstructor(name)).toMap

  "Value type parser" should "parse a parameterless definition" in {
    val events = Events("abc")
    val parser = RootValueTypeParser(types)
    val result = events.foldLeft[Parser](parser) { (p, event) => p(event) }
    result shouldBe parser
    parser.result shouldEqual TestValueType("abc", Nil)
  }

  it should "parse definition with parameters" in {
    val events = Events("abc[abc[], def[abc, ghi[jkl]], mno]")
    val parser = RootValueTypeParser(types)
    val result = events.foldLeft[Parser](parser) { (p, event) => p(event) }
    result shouldBe parser
    parser.result shouldEqual
      TestValueType("abc", List(
        TestValueType("abc", Nil),
        TestValueType("def", List(
          TestValueType("abc", Nil),
          TestValueType("ghi", List(TestValueType("jkl", Nil))))),
        TestValueType("mno", Nil)))
  }
}
