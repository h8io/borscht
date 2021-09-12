package borscht.typed.types

import borscht.test.scalar
import borscht.typed.{Ref, RefInt}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class PropTypeTest extends AnyFlatSpec with Matchers:
  "RefTypeProp" should "retrieve a typed value without parameters" in {
    System.setProperty("answer", "int:42")
    RefTypeProp.parser(Nil) map (_(scalar("answer"))) shouldEqual Right(RefInt(42))
  }

  it should "retrieve a string value without parameters" in {
    System.setProperty("city", "R'lyeh")
    RefTypeProp.parser(Nil) map (_(scalar("city"))) shouldEqual Right(Ref("R'lyeh"))
  }

  it should "retrieve a string value with the string parser parameter" in {
    System.setProperty("prayer", "Cthulhu fhtagn")
    RefTypeProp.parser(List(RefTypeString)) map (_(scalar("prayer"))) shouldEqual Right(Ref("Cthulhu fhtagn"))
  }

  it should "retrieve an integer value" in {
    System.setProperty("factorial", "3628800")
    RefTypeProp.parser(List(RefTypeInt)) map (_(scalar("factorial"))) shouldEqual Right(Ref(3628800))
  }

  it should "retrieve a nested value" in {
    System.setProperty("secret-ref", "secret-answer")
    System.setProperty("secret-answer", "42")
    val propInt = RefTypeProp.parser(List(RefTypeInt)) getOrElse fail()
    propInt(scalar("secret-answer")) shouldEqual Ref(42)
    RefTypeProp.parser(List(propInt)) map (_(scalar("secret-ref"))) shouldEqual Right(Ref(42))
  }
