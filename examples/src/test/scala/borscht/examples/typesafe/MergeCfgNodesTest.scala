package borscht.examples.typesafe

import borscht.CfgNode
import borscht.impl.typesafe.TypesafeRecipe.given
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class MergeCfgNodesTest extends AnyFlatSpec with Matchers:
  val cfg1 =
    cfg"""
         |cfg1-property: cfg1-value
         |merged-property {
         |  overwritten: cfg1-merged-property-overwritten-value
         |  replaced: [1, 2, 3]
         |  deep {
         |    name: Lovecraft
         |    city {
         |      name: Arkham
         |    }
         |    race: "Deep Ones"
         |  }
         |}
         |"""
  val cfg2 =
    cfg"""
         |cfg2-property: cfg2-value
         |merged-property {
         |  overwritten: cfg2-merged-property-overwritten-value
         |  replaced: "123"
         |  deep {
         |    city: Innsmouth
         |  }
         |}
         |"""
  val merged = cfg1 ++ cfg2

  "Merged configs" should "have correct properties" in {
    test(merged)
  }

  they should "have correct properties for the empty reference" in {
    test(merged[CfgNode]())
  }

  private def test(config: CfgNode) =
    merged[String]("cfg1-property") mustEqual "cfg1-value"
    merged[String]("cfg2-property") mustEqual "cfg2-value"
    merged[String]("merged-property", "overwritten") mustEqual "cfg2-merged-property-overwritten-value"
    merged[CfgNode]("merged-property")[String]("overwritten") mustEqual "cfg2-merged-property-overwritten-value"
    merged[String]("merged-property", "replaced") mustEqual "123"
    merged[CfgNode]("merged-property")[String]("replaced") mustEqual "123"
    merged.map[String]("merged-property", "deep") mustEqual
      Map("name" -> "Lovecraft", "city" -> "Innsmouth", "race" -> "Deep Ones")
    merged.get[String]("merged-property", "deep", "city", "name") mustBe None
