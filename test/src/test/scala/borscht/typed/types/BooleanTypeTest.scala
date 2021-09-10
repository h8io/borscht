package borscht.typed.types

import borscht.test.scalar
import borscht.typed.Ref
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class BooleanTypeTest extends AnyFlatSpec with Matchers:
  "Value type boolean" should "parse string values correctly" in {
    RefTypeBoolean.parser(Nil) map (_(scalar("true"))) shouldBe Right(Ref(true))
    RefTypeBoolean.parser(Nil) map (_(scalar("false"))) shouldBe Right(Ref(false))
  }

  it should "parse integer values correctly" in {
    RefTypeBoolean.parser(Nil) map (_(scalar(1))) shouldBe Right(Ref(true))
    RefTypeBoolean.parser(Nil) map (_(scalar(0))) shouldBe Right(Ref(false))
  }

  it should "parse boolean values correctly" in {
    RefTypeBoolean.parser(Nil) map (_(scalar(true))) shouldBe Right(Ref(true))
    RefTypeBoolean.parser(Nil) map (_(scalar(false))) shouldBe Right(Ref(false))
  }
