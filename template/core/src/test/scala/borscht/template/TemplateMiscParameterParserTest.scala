package borscht.template

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class TemplateMiscParameterParserTest extends AnyFlatSpec with Matchers:
  private val parser = TemplateParameterParser(TemplateParameterParser.MiscParsers)

  "Untyped parser" should "return the same value" in {
    parser("untyped string") mustEqual "untyped string"
  }
  
  "System properties parser" should "return a correct value" in {
    parser("prop:java.version") mustEqual sys.props("java.version") 
  }

  "Environment variables parser" should "return a correct value" in {
    parser("env:USER") mustEqual sys.env("USER")
  } 

