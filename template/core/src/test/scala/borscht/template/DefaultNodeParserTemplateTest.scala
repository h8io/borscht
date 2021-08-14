package borscht.template

import borscht.{Node, NodeParser, ScalarNode}
import borscht.parsers.NodeParserComponentRef
import borscht.reflect.ComponentRef
import borscht.test.{cfg, scalar}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class DefaultNodeParserTemplateTest extends AnyFlatSpec with Matchers:
  private val enginesCfg = cfg(
    "test1" -> cfg("class" -> classOf[TestTemplateEngine].getName, "parameters" -> "test1"),
    "test2" -> cfg("class" -> classOf[TestTemplateEngine].getName, "parameters" -> "test2"))

  private def parserWithDefault = DefaultNodeParserTemplate(enginesCfg, scalar("test1"))

  private def parserWithoutDefault = DefaultNodeParserTemplate(enginesCfg)

  extension (parser: DefaultNodeParserTemplate)
    def test(node: Node): TestTemplate = parser.apply(node).asInstanceOf[TestTemplate]

  "Default template parser with default engine" should "parse a scalar value" in {
    val tmpl = parserWithDefault.test(scalar("template text"))
    tmpl.engine shouldBe "test1"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }

  it should "parse a cfg value without defined engine" in {
    val tmpl = parserWithDefault.test(cfg("template" -> "template text"))
    tmpl.engine shouldBe "test1"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }

  it should "parse a cfg value with defined engine" in {
    val tmpl = parserWithDefault.test(cfg("template" -> "template text", "engine" -> "test2"))
    tmpl.engine shouldBe "test2"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }

  "Default template parser without default engine" should "fail on a scalar value" in {
    a[TemplateEngineException] should be thrownBy parserWithoutDefault(scalar("template text"))
  }

  it should "fail on a cfg value without defined engine" in {
    a[TemplateEngineException] should be thrownBy parserWithoutDefault(cfg("template" -> "template text"))
  }

  it should "parse a cfg value with defined engine" in {
    val tmpl = parserWithoutDefault.test(cfg("template" -> "template text", "engine" -> "test2"))
    tmpl.engine shouldBe "test2"
    tmpl.template shouldBe "template text"
    tmpl.parameters shouldBe empty
  }

  "Default template parser" should "parse a template with parameters" in {
    val tmpl = parserWithDefault.test(
      cfg("template" -> "template text", "parameters" -> cfg("a" -> "b", "x" -> "y")))
    tmpl.engine shouldBe "test1"
    tmpl.template shouldBe "template text"
    tmpl.parameters map {
      case (key: String, scalar: ScalarNode) => key -> scalar.value
      case (key: String, node: Node) => fail(s"Unexpected node type ${node.`type`} for $key")
      case (key: String, value: Any) => fail(s"Unexpected value $value for $key")
    } shouldEqual Map("a" -> "b", "x" -> "y")
  }

  it should "fail if there is no template definition" in {
    a[TemplateEngineException] should be thrownBy
      parserWithDefault(cfg("parameters" -> cfg("a" -> "b", "x" -> "y")))
  }

  it should "fail on creation if default engine is unknown" in {
    a[TemplateEngineException] should be thrownBy DefaultNodeParserTemplate(enginesCfg, scalar("test3"))
  }

  it should "fail on unknown engine in the template definition" in {
    a[TemplateEngineException] should be thrownBy
      parserWithDefault(cfg("template" -> "template text", "engine" -> "test3"))
  }

  it should "be retrieved as a component" in {
    val parser = cfg("class" -> classOf[DefaultNodeParserTemplate].getName,
      "parameters" -> cfg(
        "underlying" -> enginesCfg,
        "default" -> "test2"))[ComponentRef[NodeParser[Template]]]().get.asInstanceOf[DefaultNodeParserTemplate]

    val scalarTmpl = parser.test(scalar("template text"))
    scalarTmpl.engine shouldBe "test2"
    scalarTmpl.template shouldBe "template text"
    scalarTmpl.parameters shouldBe empty

    val cfgWithoutEngineTmpl = parser.test(cfg("template" -> "template text"))
    cfgWithoutEngineTmpl.engine shouldBe "test2"
    cfgWithoutEngineTmpl.template shouldBe "template text"
    cfgWithoutEngineTmpl.parameters shouldBe empty

    val cfgWithEngineTmpl = parser.test(cfg("template" -> "template text", "engine" -> "test1"))
    cfgWithEngineTmpl.engine shouldBe "test1"
    cfgWithEngineTmpl.template shouldBe "template text"
    cfgWithEngineTmpl.parameters shouldBe empty
  }
