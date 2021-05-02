package borscht.typed

import borscht.typed.types.{TestValueType, TestValueTypeConstructor, ValueTypeConstructor}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueTypeParserTest extends AnyFlatSpec with Matchers :
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

  it should "throw exception for the single comma in the empty parameter list" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[,]")).event shouldBe Event.TypeListSeparator(4)
  }

  it should "throw exception for the extra comma in the beginning of a parameter list" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[,def, ghi]")).event shouldBe Event.TypeListSeparator(4)
  }

  it should "throw exception for the extra comma in the end of a parameter list" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[def, ghi,]")).event shouldBe Event.TypeListEnd(13)
  }

  it should "throw exception for the double comma" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[def,, ghi]")).event shouldBe Event.TypeListSeparator(8)
  }

  it should "throw exception for a missed comma" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[def ghi]")).event shouldBe Event.TypeName("ghi", 8)
  }

  it should "throw exception for double left square brackets" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[[def, ghi]")).event shouldBe Event.TypeListStart(4)
  }

  it should "throw exception for unbalanced left square brackets" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[def[ghi, jkl]")).event shouldBe Event.End(17)
  }

  it should "throw exception for unbalanced right square brackets" in {
    (the[UnexpectedEvent] thrownBy ValueTypeParser(types)("abc[def, ghi]]")).event shouldBe Event.TypeListEnd(13)
  }

  it should "throw exception for an unknown type" in {
    (the[UnknownValueType] thrownBy ValueTypeParser(types)("abc[def, xyz]")).event shouldBe Event.TypeName("xyz", 9)
  }
