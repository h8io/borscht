package borscht.typed

import borscht.test.{cfg, scalar}
import borscht.typed.parser.UnknownTypeException
import borscht.typed.types.{DefaultRefTypes, TestNodeParser, TestRefType}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ParsersTest extends AnyFlatSpec with Matchers:
  private val types = Iterator("abc", "def", "ghi", "jkl", "mno").map(name => name -> TestRefType(name)).toMap

  "parseType function" should "parse a parameterless definition" in {
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

  it should "return the parser to the primitive value" in {
    val parser = parseType("int", DefaultRefTypes)
    parser(scalar("42")) shouldEqual RefInt(42)
  }

  "parseRef function" should "return the primitive value from the scalar node" in {
    parseRef(scalar("int:42")) shouldEqual RefInt(42)
  }

  it should "return the primitive value from the cfg node" in {
    parseRef(cfg("int" -> 42)) shouldEqual RefInt(42)
  }
