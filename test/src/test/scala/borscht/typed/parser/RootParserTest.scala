package borscht.typed.parser

import borscht.typed.ValueType
import borscht.typed.parser.{AfterParser, Events, UnknownTypeException}
import borscht.typed.types.{TestValueParser, TestValueType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.annotation.tailrec

class RootParserTest extends AnyFlatSpec with Matchers:
  private val types = Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestValueType(name)).toMap

  "Root type parser" should "parse a parameterless definition" in {
    val events = Events("abc")
    val afterParser = events.apply(RootParser(types))
    afterParser shouldBe an[AfterParser]
    afterParser.result shouldEqual Some(TestValueParser("abc", Nil))
  }

  it should "parse definition with parameters" in {
    val events = Events("abc[abc[], def[abc, ghi[jkl]], mno]")
    val afterParser = events.apply(RootParser(types))
    afterParser shouldBe an[AfterParser]
    afterParser.result shouldEqual
      Some(TestValueParser("abc", List(
        TestValueParser("abc", Nil),
        TestValueParser("def", List(
          TestValueParser("abc", Nil),
          TestValueParser("ghi", List(TestValueParser("jkl", Nil))))),
        TestValueParser("mno", Nil))))
  }

  it should "return None on a missed type" in {
    val events = Events("abc[abc[], def[abc, ghi[jkl]], miss]")
    val e = the[UnknownTypeException] thrownBy events.apply(RootParser(types))
    e.`type` shouldBe "miss"
  }
