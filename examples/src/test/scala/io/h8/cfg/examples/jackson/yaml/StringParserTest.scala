package io.h8.cfg.examples.jackson.yaml

import io.h8.cfg.parsers

import io.h8.cfg.parsers.given
import io.h8.cfg.impl.jackson.yaml.YamlFactory.given
import io.h8.cfg.template.NodeParserTemplateString
import io.h8.cfg.template.impl.st4.NodeParserST4TemplateString
import io.h8.cfg.{CfgNodeParserException, Factory}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class StringParserTest extends AnyFlatSpec with Matchers:
  val config = cfg"""
    |scalar: <value>
    |object:
    |  template: "<name>: <value> (<date; format=\\"dd.MM.yyyy\\">)"
    |  parameters:
    |    name: value
    |    value: 42
    |    date: "date::2020-12-08""""

  "Plain string parser" should "return the string for the scalar node" in {
    import io.h8.cfg.parsers.default.given
    config[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    import io.h8.cfg.parsers.default.given
    a[CfgNodeParserException] must be thrownBy (config[String]("object"))
  }

  "Template string parser" should "return the string for the scalar node" in {
    import io.h8.cfg.examples.parsers.st4.default.given
    config[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    import io.h8.cfg.examples.parsers.st4.default.given
    config[String]("object") mustEqual "value: 42 (08.12.2020)"
  }
