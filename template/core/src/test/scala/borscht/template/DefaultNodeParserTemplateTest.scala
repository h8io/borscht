package borscht.template

import borscht.{Node, NodeParser}
import borscht.test.{cfg, scalar}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DefaultNodeParserTemplateTest extends AnyFlatSpec with Matchers:
  private val enginesCfg = cfg("underlying" -> cfg(
    "test1" -> cfg(
      "class" -> classOf[TestTemplateEngine].getName,
      "parameters" -> "test1"),
    "test2" -> cfg(
      "class" -> classOf[TestTemplateEngine].getName,
      "parameters" -> "test2")))

  private val parserWithDefault = DefaultNodeParserTemplate(cfg("default" -> "test1" :: enginesCfg.toList: _*))

  private lazy val parserWithoutDefault = DefaultNodeParserTemplate(enginesCfg)

  extension (parser: DefaultNodeParserTemplate)
    def test(node: Node): TestTemplate = parser.apply(node).asInstanceOf[TestTemplate]

  "Default template parser with default engine" should "parse a scalar value" in {
    val tmpl = parserWithDefault.test(scalar("template text"))
    tmpl.engine shouldBe "test1"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }

  it should "parse a cfg value without a defined engine" in {
    val tmpl = parserWithDefault.test(cfg("template" -> "template text"))
    tmpl.engine shouldBe "test1"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }

  it should "parse a cfg value with a defined engine" in {
    val tmpl = parserWithDefault.test(cfg("template" -> "template text", "engine" -> "test2"))
    tmpl.engine shouldBe "test2"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }

  "Default template parser without default engine" should "fail on a scalar value" in {
    a[TemplateEngineException] should be thrownBy parserWithoutDefault(scalar("template text"))
  }

  it should "parse cfg value without defined engine" in {
    a[TemplateEngineException] should be thrownBy parserWithDefault(cfg("template" -> "template text"))
  }

  it should "parse cfg value with defined engine" in {
    val tmpl = parserWithoutDefault.test(cfg("template" -> "template text", "engine" -> "test2"))
    tmpl.engine shouldBe "test2"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }
