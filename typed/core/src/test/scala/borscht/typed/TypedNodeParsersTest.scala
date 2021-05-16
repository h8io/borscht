package borscht.typed

import borscht.impl.typesafe.TypesafeRecipe.given
import borscht.NodeParser
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.time.{LocalDate, LocalTime, ZonedDateTime}
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor

class TypedNodeParsersTest extends AnyFlatSpec with Matchers :
  given ValueTypeParser = DefaultValueTypeParser
  given NodeParser[TypedValue] = createNodeParserTypedValue

  "Typed value node parser" should "return correct values" in {
    System.setProperty("prayer", "Cthulhu fhtagn")
    System.setProperty("city", "R'lyeh")
    System.setProperty("factorial", "3628800")
    System.setProperty("secret-ref", "secret-answer")
    System.setProperty("secret-answer", "42")
    cfg"""
         |untyped-value: "Plain string"
         |underline: "_:String with underline prefix"
         |str: "str:String with \\"str\\" prefix"
         |int: "int:42"
         |untyped-property: "prop:prayer"
         |string-property1: "prop[_]:city"
         |string-property2: "prop[str]:city"
         |bigint-property: "prop[bigint]:factorial"
         |secret-property: "prop[prop[long]]:secret-ref"
         |"""[Map[String, TypedValue]]() map { (key, value) => key -> value.value } shouldEqual Map(
      "untyped-value" -> "Plain string",
      "underline" -> "String with underline prefix",
      "str" -> """String with "str" prefix""",
      "int" -> 42,
      "untyped-property" -> "Cthulhu fhtagn",
      "string-property1" -> "R'lyeh",
      "string-property2" -> "R'lyeh",
      "bigint-property" -> BigInt(3628800),
      "secret-property" -> 42L
    )
  }

  it should "return correct datetime values" in {
    val config = cfg"""
         |date: "date:2021-05-03"
         |time: "time:12:19:11"
         |datetime: "datetime:2021-05-03T12:20:27+09:00"
         |"""[Map[String, TypedValue]]() map { (key, value) => key -> value.value }

    LocalDate.from(config("date").asInstanceOf[TemporalAccessor]) shouldEqual LocalDate.parse("2021-05-03")
    LocalTime.from(config("time").asInstanceOf[TemporalAccessor]) shouldEqual LocalTime.parse("12:19:11")
    ZonedDateTime.from(config("datetime").asInstanceOf[TemporalAccessor]) shouldEqual
      ZonedDateTime.parse("2021-05-03T12:20:27+09:00")
  }
