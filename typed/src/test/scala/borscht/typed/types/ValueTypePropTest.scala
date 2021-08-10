package borscht.typed.types

import borscht.test.scalar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueTypePropTest extends AnyFlatSpec with Matchers:
  System.setProperty("city", "R'lyeh")
  System.setProperty("factorial", "3628800")
  System.setProperty("secret-ref", "secret-answer")
  System.setProperty("secret-answer", "42")

  "ValueTypeProp" should "retrieve a string value without parameters" in {
    ValueTypeProp().parser(Nil) map (_(scalar("city"))) shouldEqual Right("R'lyeh")
  }

  it should "retrieve a string value with the string parser parameter" in {
    ValueTypeProp().parser(List(ValueTypeString())) map (_(scalar("city"))) shouldEqual Right("R'lyeh")
  }

  it should "retrieve an integer value" in {
    ValueTypeProp().parser(List(ValueTypeInt())) map (_(scalar("factorial"))) shouldEqual Right(3628800)
  }

  it should "retrieve a nested value" in {
    val prop = ValueTypeProp()
    val propInt = prop.parser(List(ValueTypeInt())) getOrElse fail()
    propInt(scalar("secret-answer")) shouldEqual 42
    prop.parser(List(propInt)) map (_(scalar("secret-ref"))) shouldEqual Right(42)
  }
