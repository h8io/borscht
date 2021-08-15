package borscht.typed.types

import borscht.Meta
import borscht.test.{cfg, seq}
import borscht.typed.{DefaultNodeParserValueParser, DefaultNodeParserValueRef}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Right

class ValueTypeComponentRefTest extends AnyFlatSpec with Matchers:
  "Component reference value type" should "create a correct component instance" in {
    ValueTypeComponentRef().parser(Nil) map { parser =>
      parser(
        cfg(
          "class" -> classOf[RuntimeException].getName,
          "parameters" -> seq(cfg("type" -> "str", "value" -> "Exception component"))).withMeta(
          Meta(
            None,
            Some(DefaultNodeParserValueParser(Map("str" -> ValueTypeString()))),
            Some(DefaultNodeParserValueRef()))))
    } match
      case Right(e: RuntimeException) => e.getMessage shouldEqual "Exception component"
      case unexpected => fail(s"Unexpected component $unexpected")
  }
