package borscht.examples.jackson.yaml

import borscht.NodeParserException
import borscht.impl.jackson.yaml.YamlRecipe
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ST4StringParserTest extends AnyFlatSpec with Matchers:
  private val config =
    YamlRecipe(java.nio.file.Paths.get(getClass.getResource(s"${getClass.getSimpleName}.yaml").toURI))

  "Template string parser" should "return the string for the scalar node" in {
    config[String]("scalar") mustEqual "<value>"
  }

  it should "transform an object to string" in {
    config[String]("object") mustEqual "value: 42 (08.12.2020)"
  }
