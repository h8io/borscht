package borscht.examples.typesafe

import borscht.NodeParserException
import borscht.impl.typesafe.TypesafeRecipe
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ST4StringParserTest extends AnyFlatSpec with Matchers:
  private val config =
    TypesafeRecipe(java.nio.file.Paths.get(getClass.getResource(s"${getClass.getSimpleName}.conf").toURI))

  "ST4 template string parser" should "return the string for the scalar node" in {
    config[String]("scalar") mustEqual "<value>"
  }

  it should "transform an object to string" in {
    config[String]("object") mustEqual "value: 42 (08.12.2020)"
  }
