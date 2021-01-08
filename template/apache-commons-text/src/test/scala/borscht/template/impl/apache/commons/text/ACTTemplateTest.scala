package borscht.template.impl.apache.commons.text

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ACTTemplateTest extends AnyFlatSpec with Matchers:
  "Apache Commons Text template" should "render a template correctly" in {
    ACTTemplateParser()("p1 = ${ph:property1}, p2 = ${ph:property2}, user = ${env:USER}").apply(
      "property1" -> "value1", "property2" -> "value2") mustEqual
    s"p1 = value1, p2 = value2, user = ${sys.env("USER")}"
  }

  it should "accept keys with parameters" in {
    ACTTemplateParser()(
      "p1 = ${ph:property1 format=\"%s\"}, p2 = ${ph:property2}, p3 = ${ph:property3 null=\"null here\"}").apply(
      "property1" -> "value1", "property2" -> "value2") mustEqual
      "p1 = value1, p2 = value2, p3 = null here"
  }

  it should "accept keys with list parameters" in {
    ACTTemplateParser()("p1 = ${ph:property1 format=\"%02d\" seq.separator=\" | \" seq.start=\"\" seq.end=\"\"}")
      .apply("property1" -> List(1, 2, 3)) mustEqual "p1 = 01 | 02 | 03"
  }

  it should "accept keys with nullable list parameters" in {
    ACTTemplateParser()("p1 = ${ph:property1 format=\"%02d\" separator=\" # \" null=???}")
      .apply("property1" -> List(1, null, 3)) mustEqual "p1 = [01 # ??? # 03]"
  }

  it should "accept keys with product parameters" in {
    ACTTemplateParser()("p1 = ${ph:property1 format=\"%02d\" product.separator=\" | \"}")
      .apply("property1" -> (1, 2, 3)) mustEqual "p1 = (01 | 02 | 03)"
  }

  it should "accept keys with nullable product parameters" in {
    ACTTemplateParser()(
      "p1 = ${ph:property1 format=\"%02d\" product.separator=\" # \" product.start=\"\" product.end=\"\"}")
      .apply("property1" -> (None, 2, 3)) mustEqual "p1 = null # 02 # 03"
  }
