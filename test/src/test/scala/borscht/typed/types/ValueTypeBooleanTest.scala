package borscht.typed.types

import borscht.test.scalar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Right

class ValueTypeBooleanTest extends AnyFlatSpec with Matchers:
  "Value type boolean" should "parse string values correctly" in {
    ValueTypeBoolean.parser(Nil) map (_(scalar("true"))) shouldBe Right(true)
    ValueTypeBoolean.parser(Nil) map (_(scalar("false"))) shouldBe Right(false)
  }

  it should "parse integer values correctly" in {
    ValueTypeBoolean.parser(Nil) map (_(scalar(1))) shouldBe Right(true)
    ValueTypeBoolean.parser(Nil) map (_(scalar(0))) shouldBe Right(false)
  }

  it should "parse boolean values correctly" in {
    ValueTypeBoolean.parser(Nil) map (_(scalar(true))) shouldBe Right(true)
    ValueTypeBoolean.parser(Nil) map (_(scalar(false))) shouldBe Right(false)
  }
