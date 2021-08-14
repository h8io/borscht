package borscht.examples.act

import borscht.{CfgNodeParserException, Meta}
import borscht.impl.typesafe.TypesafeRecipe
import borscht.impl.typesafe.TypesafeRecipe.given
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ACTStringParserTest extends AnyFlatSpec with Matchers:
  private val config =
    TypesafeRecipe(java.nio.file.Paths.get(getClass.getResource("ACTStringParserTest.conf").toURI))

  println(config)

  System.setProperty("birthday", "1974-05-03")

  "ACT template string parser" should "return the string for the scalar node" in {
    config[String]("scalar") mustEqual "<value>"
  }

  it should "transform an object to string" in {
    config[String]("object") mustEqual "value: 42 (03.05.1974)"
  }
