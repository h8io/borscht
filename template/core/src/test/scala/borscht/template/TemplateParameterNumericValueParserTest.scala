package borscht.template

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class TemplateParameterNumericValueParserTest extends AnyFlatSpec with Matchers:
  private val parser = TemplateParameterValueParser(parsers = TemplateParameterValueParser.NumericParsers) 

  "Template parameter value parser" should "parse a big integer value" in {
    parser("bigint::1234567890987654321") must (be (a[BigInt]) and equal (BigInt("1234567890987654321")))
  }

  it should "parse a big decimal value" in {
    parser("bigdecimal::1234567890987654321.42") must
      (be (a[BigDecimal]) and equal (BigDecimal("1234567890987654321.42")))
  }

  it should "parse a byte value" in {
    parser("byte::42") must (be (a[java.lang.Byte]) and equal (42))
  }

  it should "parse a double value" in {
    parser("double::42.25") must (be (a[java.lang.Double]) and equal (42.25))
  }

  it should "parse a float value" in {
    parser("float::42.5") must (be (a[java.lang.Float]) and equal (42.5))
  }

  it should "parse an integer value" in {
    parser("int::100") must (be (an[Integer]) and equal (100))
  }

  it should "parse a long value" in {
    parser(s"long::${Long.MinValue}") must (be (a[java.lang.Long]) and equal (Long.MinValue))
  }

  it should "parse a short value" in {
    parser(s"short::+${Short.MaxValue}") must (be (a[java.lang.Short]) and equal (Short.MaxValue))
  }
