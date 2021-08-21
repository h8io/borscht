package borscht.typed.types

import borscht.test.{cfg, scalar, seq}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueTypeListTest extends AnyFlatSpec with Matchers:
  "List value type without type parameter" should "parse a plain string list" in {
    ValueTypeList.parser(Nil) map (_(seq("abc", "defg", "hijkl"))) shouldEqual
      Right(List("abc", "defg", "hijkl"))
  }

  it should "parse a scalar string" in {
    ValueTypeList.parser(Nil) map (_(scalar("ありがとう"))) shouldEqual Right(List("ありがとう"))
  }

  it should "parse typed values" in {
    ValueTypeList.parser(Nil) map (_(seq("int:42", cfg("$" -> "Answer"), "Adams", 1978))) shouldEqual
      Right(List(42, "Answer", "Adams", 1978))
  }

  "List value type with type parameter" should "parse a string list" in {
    ValueTypeList.parser(List(ValueTypeString)) map (_(seq("abc", "defg", "hijkl"))) shouldEqual
      Right(List("abc", "defg", "hijkl"))
  }

  it should "parse a scalar integer" in {
    ValueTypeList.parser(List(ValueTypeInt)) map (_(scalar(42))) shouldEqual Right(List(42))
  }

  it should "parse typed values" in {
    ValueTypeList.parser(List(ValueTypeBoolean)) map (_(seq(true, "false", "true", false))) shouldEqual
      Right(List(true, false, true, false))
  }

  it should "parse list of lists" in {
    ValueTypeList.parser(List(ValueTypeList.parser(List(ValueTypeLong)) getOrElse (fail()))) map (_(seq(
      seq(1, 2, 3),
      seq(6, 5, 4, 7),
      seq(8, 9, 11, 10, 12)))) shouldEqual
      Right(List(List(1, 2, 3), List(6, 5, 4, 7), List(8, 9, 11, 10, 12)))
  }

