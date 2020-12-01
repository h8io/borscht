package borscht.examples.typesafe

import borscht._
import borscht.impl.typesafe.TypesafeConfigProvider
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class NodeReferenceTest extends AnyFlatSpec with Matchers:
  private val recipe = Recipe(provider = TypesafeConfigProvider())
  import recipe.given

  private val config =
    cfg"""
         |level1: true
         |object {
         |  level2: 42
         |  cfg {
         |    level3 {
         |      group: Tiamat
         |      album: "Sumerian Cry"
         |    }
         |  }
         |}
         |"""

  "Config" should "provide a plain value" in {
    config[Boolean]("level1") mustBe true
  }

  it should "provide a nested value" in {
    config[Int]("object", "level2") mustEqual 42
  }

  it should "provide a zero level value" in {
    val node = config[ConfigNode]("object", "cfg", "level3")
    node mustBe a[ConfigNode]
    node[Map[String, String]]() mustEqual Map("group" -> "Tiamat", "album" -> "Sumerian Cry")
  }
