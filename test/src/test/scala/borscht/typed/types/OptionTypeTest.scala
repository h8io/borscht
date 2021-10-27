package borscht.typed.types

import borscht.test.{cfg, scalar, seq}
import borscht.typed.RefObj
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class OptionTypeTest extends AnyFlatSpec with Matchers:
  "Option value type without type parameter" should "parse an empty list" in {
    RefTypeOption.parser(List(RefTypeAny)) map (_(seq())) shouldEqual Right(RefObj[Option[Any]](None))
  }

  it should "parse a singleton integer list" in {
    RefTypeOption.parser(List(RefTypeInt)) map (_(seq(42))) shouldEqual Right(RefObj[Option[Int]](Some(42)))
  }

  it should "parse a scalar string" in {
    RefTypeOption.parser(List(RefTypeString)) map (_(scalar("ありがとう"))) shouldEqual
      Right(RefObj[Option[String]](Some("ありがとう")))
  }

  it should "parse typed values" in {
    RefTypeOption.parser(Nil) map (_(seq("int:42"))) shouldEqual Right(RefObj[Option[Int]](Some(42)))
  }
