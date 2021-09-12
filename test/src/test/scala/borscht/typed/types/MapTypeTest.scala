package borscht.typed.types

import borscht.test.{cfg, seq}
import borscht.typed.RefObj
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class MapTypeTest extends AnyFlatSpec with Matchers:
  "Map reference type without type parameters" should "parse plain cfg node" in {
    RefTypeMap.parser(Nil) map (_(cfg("city" -> "R'lyeh", "who" -> "Cthulhu"))) shouldEqual
      Right(RefObj(Map("city" -> "R'lyeh", "who" -> "Cthulhu")))
  }

  it should "parse cfg node with type prefixes" in {
    RefTypeMap.parser(Nil) map (_(cfg(
      "city" -> "str:R'lyeh",
      "year" -> "int:1925",
      "characters" -> cfg("list" -> seq("Thurston", "Angell")),
      "coordinates" -> "47°9′S 126°43′W",
      "short:1926" -> "written"))) shouldEqual
      Right(RefObj(Map(
        "city" -> "R'lyeh",
        "year" -> 1925,
        "characters" -> List("Thurston", "Angell"),
        "coordinates" -> "47°9′S 126°43′W",
        1926.toShort -> "written")))
  }

  "Map reference type with type parameters" should "parse plain cfg node" in {
    RefTypeMap.parser(List(RefTypeString, RefTypeString)) map (_(cfg(
      "city" -> "R'lyeh", "who" -> "Cthulhu"))) shouldEqual
      Right(RefObj(Map("city" -> "R'lyeh", "who" -> "Cthulhu")))
  }

  it should "parse cfg node with type prefixes" in {
    RefTypeMap.parser(List(RefTypeString, RefTypeString)) map (_(cfg(
      "city" -> "str:R'lyeh",
      "year" -> "int:1925",
      "coordinates" -> "47°9′S 126°43′W",
      "short:1926" -> "written"))) shouldEqual
      Right(RefObj(Map(
        "city" -> "str:R'lyeh",
        "year" -> "int:1925",
        "coordinates" -> "47°9′S 126°43′W",
        "short:1926" -> "written")))
  }
