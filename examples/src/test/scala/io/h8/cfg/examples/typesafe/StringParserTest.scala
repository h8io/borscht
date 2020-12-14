package io.h8.cfg.examples.typesafe

import io.h8.cfg.parsers.given
import io.h8.cfg.{CfgNodeParserException, Factory}
import io.h8.cfg.impl.typesafe.TypesafeFactory.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class StringParserTest extends AnyFlatSpec with Matchers:
  val config = cfg"""
    |scalar: <value>
    |object {
    |  template: "<name>: <value> (<date; format=\\"dd.MM.yyyy\\">)"
    |  parameters {
    |    name: value
    |    value: 42
    |    date: "date::2020-12-08"
    |  }
    |}"""

  "Plain string parser" should "return the string for the scalar node" in {
    config[String]("scalar") mustEqual "<value>"
  }

  it should "throw a parser exception for the config node" in {
    a[CfgNodeParserException] must be thrownBy (config[String]("object"))
  }

  "Template string parser" should "return the string for the scalar node" in {
    import io.h8.cfg.examples.parsers.st4.given
    config[String]("scalar") mustEqual "<value>"
  }

  it should "transform an object to string" in {
    import io.h8.cfg.examples.parsers.st4.given
    config[String]("object") mustEqual "value: 42 (08.12.2020)"
  }
