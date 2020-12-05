package io.h8.cfg.examples.typesafe

import io.h8.cfg.parsers.given
import io.h8.cfg.{CfgNodeParserException, Factory}
import io.h8.cfg.impl.typesafe.TypesafeFactory
import io.h8.cfg.template.NodeParserTemplateString
import io.h8.cfg.template.impl.st4.NodeParserST4TemplateString
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class StringParserTest extends AnyFlatSpec with Matchers:
  private def config(factory: Factory) = factory.parse("""
    |scalar: <value>
    |object {
    |  template: "<name>: <value>"
    |  parameters {
    |    name: value
    |    value: 42
    |  }
    |}""".stripMargin)

  "Plain string parser" should "return the string for the scalar node" in {
    val factory = TypesafeFactory()
    import factory.given

    config(factory)[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    val factory = TypesafeFactory()
    import factory.given

    a[CfgNodeParserException] must be thrownBy (config(factory)[String]("object"))
  }

  "Template string parser" should "return the string for the scalar node" in {
    val factory = TypesafeFactory(NodeParserST4TemplateString())
    import factory.given

    config(factory)[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    val factory = TypesafeFactory(NodeParserST4TemplateString())
    import factory.given

    config(factory)[String]("object") mustEqual "value: 42"
  }
