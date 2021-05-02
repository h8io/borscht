package borscht.typed.events

import borscht.typed.types.{TestValueType, TestValueTypeConstructor, ValueTypeConstructor}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RootParserTest extends AnyFlatSpec with Matchers {
  private val types: Map[String, ValueTypeConstructor] =
    Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueTypeConstructor(name)).toMap

  "Root type parser" should "parse a parameterless definition" in {
    val events = Events("abc")
    val parser = RootParser(types)
    val result = events.foldLeft[Parser](parser) { (p, event) => p(event) }
    result shouldBe parser
    parser.result shouldEqual TestValueType("abc", Nil)
  }

  it should "parse definition with parameters" in {
    val events = Events("abc[abc[], def[abc, ghi[jkl]], mno]")
    val parser = RootParser(types)
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
