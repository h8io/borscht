package borscht.typed.types

import borscht.test.scalar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Right

class NumericTypesTest extends AnyFlatSpec with Matchers:
  "BigInt value type" should "parse big integers correctly" in {
    ValueTypeBigInt()(Nil) map (_(scalar("42"))) shouldEqual Right(BigInt(42))
    ValueTypeBigInt()(Nil) map (_(scalar(BigInt(42)))) shouldEqual Right(BigInt(42))
    ValueTypeBigInt()(Nil) map (_(scalar(42))) shouldEqual Right(BigInt(42))
  }

  "BigDecimal value type" should "parse big decimals correctly" in {
    ValueTypeBigDecimal()(Nil) map (_(scalar("3.14159"))) shouldEqual Right(BigDecimal(3.14159))
    ValueTypeBigDecimal()(Nil) map (_(scalar(BigDecimal(3.14159)))) shouldEqual Right(BigDecimal(3.14159))
    ValueTypeBigDecimal()(Nil) map (_(scalar(3.14159))) shouldEqual Right(BigDecimal(3.14159))
  }

  "Byte value type" should "parse bytes correctly" in {
    ValueTypeByte()(Nil) map (_(scalar("42"))) shouldEqual Right(42.toByte)
    ValueTypeByte()(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(42.toByte)
    ValueTypeByte()(Nil) map (_(scalar(42.toByte))) shouldEqual Right(42.toByte)
  }

  "Double value type" should "parse doubles correctly" in {
    ValueTypeDouble()(Nil) map (_(scalar("3.14159"))) shouldEqual Right(3.14159)
    ValueTypeDouble()(Nil) map (_(scalar(BigDecimal(3.14159)))) shouldEqual Right(3.14159)
    ValueTypeDouble()(Nil) map (_(scalar(3.14159d))) shouldEqual Right(3.14159)
  }

  "Float value type" should "parse floats correctly" in {
    ValueTypeFloat()(Nil) map (_(scalar("3.14159"))) shouldEqual Right(3.14159.toFloat)
    ValueTypeFloat()(Nil) map (_(scalar(BigDecimal(3.14159)))) shouldEqual Right(3.14159.toFloat)
    ValueTypeFloat()(Nil) map (_(scalar(3.14159f))) shouldEqual Right(3.14159f)
  }

  "Integer value type" should "parse bytes correctly" in {
    ValueTypeInt()(Nil) map (_(scalar("42"))) shouldEqual Right(42)
    ValueTypeInt()(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(42)
    ValueTypeInt()(Nil) map (_(scalar(42))) shouldEqual Right(42)
  }

  "Long value type" should "parse bytes correctly" in {
    ValueTypeLong()(Nil) map (_(scalar("42"))) shouldEqual Right(42L)
    ValueTypeLong()(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(42L)
    ValueTypeLong()(Nil) map (_(scalar(42L))) shouldEqual Right(42L)
  }

  "Short value type" should "parse bytes correctly" in {
    ValueTypeShort()(Nil) map (_(scalar("42"))) shouldEqual Right(42.toShort)
    ValueTypeShort()(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(42.toShort)
    ValueTypeShort()(Nil) map (_(scalar(42.toShort))) shouldEqual Right(42.toShort)
  }
