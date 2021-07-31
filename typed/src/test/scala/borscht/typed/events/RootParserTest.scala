package borscht.typed.events

import borscht.typed.ValueType
import borscht.typed.types.{TestValueParser, TestValueType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RootParserTest extends AnyFlatSpec with Matchers {
  private val types: Map[String, ValueType] =
    Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueType(name)).toMap

  "Root type parser" should "parse a parameterless definition" in {
    val events = Events("abc")
    val parser = RootParser(types)
    val result = events.foldLeft[Parser](parser) { (p, event) => p(event) }
    result shouldBe parser
    parser.result shouldEqual TestValueParser("abc", Nil)
  }

  it should "parse definition with parameters" in {
    val events = Events("abc[abc[], def[abc, ghi[jkl]], mno]")
    val parser = RootParser(types)
    val result = events.foldLeft[Parser](parser) { (p, event) => p(event) }
    result shouldBe parser
    parser.result shouldEqual
      TestValueParser("abc", List(
        TestValueParser("abc", Nil),
        TestValueParser("def", List(
          TestValueParser("abc", Nil),
          TestValueParser("ghi", List(TestValueParser("jkl", Nil))))),
        TestValueParser("mno", Nil)))
  }
}
