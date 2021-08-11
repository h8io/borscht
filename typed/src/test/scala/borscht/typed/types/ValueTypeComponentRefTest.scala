package borscht.typed.types

import borscht.test.{cfg, seq}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Right

class ValueTypeComponentRefTest extends AnyFlatSpec with Matchers:
  "Component reference value type" should "create a correct component instance" in {
    ValueTypeComponentRef().parser(Nil) map (_(cfg("class" -> "java.lang.RuntimeException",
      "parameters" -> "Exception component"))) match
      case Right(e: RuntimeException) => e.getMessage shouldEqual "Exception component"
      case unexpected => fail(s"Unexpected component $unexpected")
  }
