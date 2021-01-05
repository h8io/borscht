package borscht.template.impl.apache.commons.text

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ACTTemplateTest extends AnyFlatSpec with Matchers:
  "Apache Commons Text template" should "render a template correctly" in {
    ACTTemplateParser()("p1 = ${cfg:property1}, p2 = ${cfg:property2}, user = ${env:USER}").apply(
      "property1" -> "value1", "property2" -> "value2") mustEqual
    s"p1 = value1, p2 = value2, user = ${sys.env("USER")}"
  }