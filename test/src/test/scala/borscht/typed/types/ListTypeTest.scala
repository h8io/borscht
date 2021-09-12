package borscht.typed.types

import borscht.test.{cfg, scalar, seq}
import borscht.typed.RefObj
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ListTypeTest extends AnyFlatSpec with Matchers:
  "List value type without type parameter" should "parse a plain string list" in {
    RefTypeList.parser(Nil) map (_(seq("abc", "defg", "hijkl"))) shouldEqual
      Right(RefObj(List("abc", "defg", "hijkl")))
  }

  it should "parse a scalar string" in {
    RefTypeList.parser(Nil) map (_(scalar("ありがとう"))) shouldEqual Right(RefObj(List("ありがとう")))
  }

  it should "parse typed values" in {
    RefTypeList.parser(Nil) map (_(seq("int:42", cfg("$" -> "Answer"), "Adams", 1978))) shouldEqual
      Right(RefObj(List(42, "Answer", "Adams", 1978)))
  }

  "List value type with type parameter" should "parse a string list" in {
    RefTypeList.parser(List(RefTypeString)) map (_(seq("abc", "defg", "hijkl"))) shouldEqual
      Right(RefObj(List("abc", "defg", "hijkl")))
  }

  it should "parse a scalar integer" in {
    RefTypeList.parser(List(RefTypeInt)) map (_(scalar(42))) shouldEqual Right(RefObj(List(42)))
  }

  it should "parse typed values" in {
    RefTypeList.parser(List(RefTypeBoolean)) map (_(seq(true, "false", "true", false))) shouldEqual
      Right(RefObj(List(true, false, true, false)))
  }

  it should "parse list of lists" in {
    RefTypeList.parser(List(RefTypeList.parser(List(RefTypeLong)) getOrElse (fail()))) map (_(seq(
      seq(1, 2, 3),
      seq(6, 5, 4, 7),
      seq(8, 9, 11, 10, 12)))) shouldEqual
      Right(RefObj(List(List(1, 2, 3), List(6, 5, 4, 7), List(8, 9, 11, 10, 12))))
  }

