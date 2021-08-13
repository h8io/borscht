package borscht.template

import borscht.test.cfg
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DefaultNodeParserTemplateTest extends AnyFlatSpec with Matchers:
  "Default template parser" should "parse scalar value" in {
    val nodeParser = DefaultNodeParserTemplate(cfg("underlying" -> cfg(
      "test1" -> cfg(
        "class" -> classOf[TestTemplateEngine].getName,
        "parameters" -> "test1"
      ),
      "test2" -> cfg(
        "class" -> classOf[TestTemplateEngine].getName,
        "parameters" -> "test2"
      ))))
  }