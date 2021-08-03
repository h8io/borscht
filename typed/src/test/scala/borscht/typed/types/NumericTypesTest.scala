package borscht.typed.types

import borscht.test.scalar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Right

class NumericTypesTest extends AnyFlatSpec with Matchers:
  "BigInt value type" should "parse big integers correctly" in {
    ValueTypeBigInt()(Nil) map (_(scalar("42"))) shouldEqual Right(BigInt(42))
    ValueTypeBigInt()(Nil) map (_(scalar(BigInt(42)))) shouldEqual Right(BigInt(42))
  }
