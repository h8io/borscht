package borscht.template.impl.apache.commons.text

import borscht.test.scalar

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ACTTemplateTest extends AnyFlatSpec with Matchers:
  "Apache Commons Text template" should "render a template correctly" in {
    ACTTemplateEngine()(scalar("p1 = ${ph:property1}, p2 = ${ph:property2}, user = ${env:USER}"))
      .set("property1" -> "value1", "property2" -> "value2").render mustEqual
    s"p1 = value1, p2 = value2, user = ${sys.env("USER")}"
  }

  it should "accept keys with parameters" in {
    ACTTemplateEngine()(scalar(
      "p1 = ${ph:property1 format=\"%s\"}, p2 = ${ph:property2}, p3 = ${ph:property3 null=\"null here\"}"
    )).set("property1" -> "value1", "property2" -> "value2").render mustEqual "p1 = value1, p2 = value2, p3 = null here"
  }

  it should "accept keys with list parameters" in {
    ACTTemplateEngine()(scalar(
      "p1 = ${ph:property1 format=\"%02d\" list.separator=\" | \" list.start=\"\" list.end=\"\"}"
    )).set("property1" -> List(1, 2, 3)).render mustEqual "p1 = 01 | 02 | 03"
  }

  it should "accept keys with nullable list parameters" in {
    ACTTemplateEngine()(scalar("p1 = ${ph:property1 format=\"%02d\" separator=\" # \" null=???}"))
      .set("property1" -> List(1, null, 3)).render mustEqual "p1 = [01 # ??? # 03]"
  }

  it should "accept keys with product parameters" in {
    ACTTemplateEngine()(scalar("p1 = ${ph:property1 format=\"%02d\" product.separator=\" | \"}"))
      .set("property1" -> (1, 2, 3)).render mustEqual "p1 = (01 | 02 | 03)"
  }

  it should "accept keys with nullable product parameters" in {
    ACTTemplateEngine()(scalar(
      "p1 = ${ph:property1 format=\"%02d\" product.separator=\" # \" product.start=\"\" product.end=\"\"}"
    )).set("property1" -> (None, 2, 3)).render mustEqual "p1 = null # 02 # 03"
  }
