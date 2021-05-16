package borscht.examples.jackson.yaml

import borscht.CfgNodeParserException
import borscht.impl.jackson.yaml.YamlRecipe.given
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ST4StringParserTest extends AnyFlatSpec with Matchers:
  private val config = cfg"""
    |scalar: <value>
    |object:
    |  template: "<name>: <value> (<date; format=\\"dd.MM.yyyy\\">)"
    |  parameters:
    |    name: value
    |    value: 42
    |    date: "date:2020-12-08""""

  "Plain string parser" should "return the string for the scalar node" in {
    config[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    a[CfgNodeParserException] must be thrownBy (config[String]("object"))
  }

  "Template string parser" should "return the string for the scalar node" in {
    import borscht.examples.parsers.given
    config[String]("scalar") mustEqual "<value>"
  }

  it should "transform an object to string" in {
    import borscht.examples.parsers.given
    config[String]("object") mustEqual "value: 42 (08.12.2020)"
  }
