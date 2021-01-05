package borscht.template

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class TemplateParameterChainParserTest extends AnyFlatSpec with Matchers {
  private val parser = TemplateParameterParser()

  "Chained system properties parser" should "return a correct value" in {
    System.setProperty("borcht.test.int-value", "42")
    val result = parser("prop>int::borcht.test.int-value")
    result mustBe an[Int]
    result mustEqual 42
  }
}
