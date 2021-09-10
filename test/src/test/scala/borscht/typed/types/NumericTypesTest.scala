package borscht.typed.types

import borscht.test.scalar
import borscht.typed.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NumericTypesTest extends AnyFlatSpec with Matchers:
  "BigInt value type" should "parse big integers correctly" in {
    RefTypeBigInt.parser(Nil) map (_(scalar("42"))) shouldEqual Right(Ref(BigInt(42)))
    RefTypeBigInt.parser(Nil) map (_(scalar(BigInt(42)))) shouldEqual Right(Ref(BigInt(42)))
    RefTypeBigInt.parser(Nil) map (_(scalar(42))) shouldEqual Right(Ref(BigInt(42)))
  }

  "BigDecimal value type" should "parse big decimals correctly" in {
    RefTypeBigDecimal.parser(Nil) map (_(scalar("3.14159"))) shouldEqual Right(Ref(BigDecimal(3.14159)))
    RefTypeBigDecimal.parser(Nil) map (_(scalar(BigDecimal(3.14159)))) shouldEqual Right(Ref(BigDecimal(3.14159)))
    RefTypeBigDecimal.parser(Nil) map (_(scalar(3.14159))) shouldEqual Right(Ref(BigDecimal(3.14159)))
  }

  "Byte value type" should "parse bytes correctly" in {
    RefTypeByte.parser(Nil) map (_(scalar("42"))) shouldEqual Right(RefByte(42))
    RefTypeByte.parser(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(RefByte(42))
    RefTypeByte.parser(Nil) map (_(scalar(42.toByte))) shouldEqual Right(RefByte(42))
  }

  "Double value type" should "parse doubles correctly" in {
    RefTypeDouble.parser(Nil) map (_(scalar("3.14159"))) shouldEqual Right(RefDouble(3.14159))
    RefTypeDouble.parser(Nil) map (_(scalar(BigDecimal(3.14159)))) shouldEqual Right(RefDouble(3.14159))
    RefTypeDouble.parser(Nil) map (_(scalar(3.14159d))) shouldEqual Right(RefDouble(3.14159))
  }

  "Float value type" should "parse floats correctly" in {
    RefTypeFloat.parser(Nil) map (_(scalar("3.14159"))) shouldEqual Right(RefFloat(3.14159))
    RefTypeFloat.parser(Nil) map (_(scalar(BigDecimal(3.14159)))) shouldEqual Right(RefFloat(3.14159))
    RefTypeFloat.parser(Nil) map (_(scalar(3.14159f))) shouldEqual Right(RefFloat(3.14159))
  }

  "Integer value type" should "parse bytes correctly" in {
    RefTypeInt.parser(Nil) map (_(scalar("42"))) shouldEqual Right(RefInt(42))
    RefTypeInt.parser(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(RefInt(42))
    RefTypeInt.parser(Nil) map (_(scalar(42))) shouldEqual Right(RefInt(42))
  }

  "Long value type" should "parse bytes correctly" in {
    RefTypeLong.parser(Nil) map (_(scalar("42"))) shouldEqual Right(RefLong(42))
    RefTypeLong.parser(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(RefLong(42))
    RefTypeLong.parser(Nil) map (_(scalar(42L))) shouldEqual Right(RefLong(42L))
  }

  "Short value type" should "parse bytes correctly" in {
    RefTypeShort.parser(Nil) map (_(scalar("42"))) shouldEqual Right(RefShort(42))
    RefTypeShort.parser(Nil) map (_(scalar(BigDecimal(42)))) shouldEqual Right(RefShort(42))
    RefTypeShort.parser(Nil) map (_(scalar(42.toShort))) shouldEqual Right(RefShort(42))
  }
