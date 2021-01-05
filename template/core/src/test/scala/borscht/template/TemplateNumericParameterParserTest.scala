package borscht.template

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class TemplateNumericParameterParserTest extends AnyFlatSpec with Matchers:
  private val parser = TemplateParameterParser(TemplateParameterParser.NumericParsers)
  
  "BigInt parser" should "return a correct value" in {
    val result = parser("bigint::1234567890987654321")
    result mustBe a[BigInt]
    result mustEqual BigInt("1234567890987654321")
  }

  "BigDecimal parser" should "return a correct value" in {
    val result = parser("bigdecimal::1234567890987654321.75")
    result mustBe a[BigDecimal]
    result mustEqual BigDecimal("1234567890987654321.75")
  }

  "Byte parser" should "return a correct value" in {
    val result = parser("byte::42")
    result mustBe a[Byte]
    result mustEqual 42
  }

  "Double parser" should "return a correct value" in {
    val result = parser("double::-2.5")
    result mustBe a[Double]
    result mustEqual -2.5d
  }

  it should "return a correct value from exponential form" in {
    val result = parser("double::2.5E-1")
    result mustBe a[Double]
    result mustEqual 0.25d
  }

  "Float parser" should "return a correct value" in {
    val result = parser("float::3.25")
    result mustBe a[Float]
    result mustEqual 3.25f
  }

  it should "return a correct value from exponential form" in {
    val result = parser("float::-3.25e1")
    result mustBe a[Float]
    result mustEqual -32.5f
  }

  "Int parser" should "return a correct value" in {
    val result = parser("int::-42")
    result mustBe an[Int]
    result mustEqual -42
  }
  
  "Long parser" should "return a correct value" in {
    val result = parser("long::42")
    result mustBe a[Long]
    result mustEqual 42
  }

  "Short parser" should "return a correct value" in {
    val result = parser("short::-42")
    result mustBe a[Short]
    result mustEqual -42
  }
