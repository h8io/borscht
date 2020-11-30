package borscht.examples

import borscht._
import borscht.impl.typesafe.TypesafeConfigProvider
import borscht.parsers.given
import borscht.template.impl.st4.NodeParserST4TemplateString
import borscht.template.NodeParserTemplateString
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class StringParserTest extends AnyFlatSpec with Matchers:
  private def config(recipe: Recipe) = recipe.parse("""
    |scalar: <value>
    |object {
    |  template: "<name>: <value>"
    |  parameters {
    |    name: value
    |    value: 42
    |  }
    |}""".stripMargin)

  "Plain string parser" should "return the string for the scalar node" in {
    val recipe = Recipe(provider = TypesafeConfigProvider())
    import recipe.given

    config(recipe)[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    val recipe = Recipe(provider = TypesafeConfigProvider())
    import recipe.given

    a[BorschtNodeParserException] must be thrownBy (config(recipe)[String]("object"))
  }

  "Template string parser" should "return the string for the scalar node" in {
    val recipe = Recipe(
      provider = TypesafeConfigProvider(),
      NodeParserString = NodeParserST4TemplateString())
    import recipe.given

    config(recipe)[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    val recipe = Recipe(
      provider = TypesafeConfigProvider(),
      NodeParserString = NodeParserST4TemplateString())
    import recipe.given

    config(recipe)[String]("object") mustEqual "value: 42"
  }
