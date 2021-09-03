package borscht.typedOld.types

import borscht.test.scalar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueTypePropTest extends AnyFlatSpec with Matchers:
  "ValueTypeProp" should "retrieve a string value without parameters" in {
    System.setProperty("city", "R'lyeh")
    ValueTypeProp.parser(Nil) map (_(scalar("city"))) shouldEqual Right("R'lyeh")
  }

  it should "retrieve a typed value without parameters" in {
    System.setProperty("answer", "int:42")
    ValueTypeProp.parser(Nil) map (_(scalar("answer"))) shouldEqual Right(42)
  }

  it should "retrieve a string value with the string parser parameter" in {
    System.setProperty("prayer", "Cthulhu fhtagn")
    ValueTypeProp.parser(List(ValueTypeString)) map (_(scalar("prayer"))) shouldEqual
      Right("Cthulhu fhtagn")
  }

  it should "retrieve an integer value" in {
    System.setProperty("factorial", "3628800")
    ValueTypeProp.parser(List(ValueTypeInt)) map (_(scalar("factorial"))) shouldEqual Right(3628800)
  }

  it should "retrieve a nested value" in {
    System.setProperty("secret-ref", "secret-answer")
    System.setProperty("secret-answer", "42")
    val propInt = ValueTypeProp.parser(List(ValueTypeInt)) getOrElse fail()
    propInt(scalar("secret-answer")) shouldEqual 42
    ValueTypeProp.parser(List(propInt)) map (_(scalar("secret-ref"))) shouldEqual Right(42)
  }
