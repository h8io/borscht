package borscht.impl.typesafe

import borscht.Recipe
import borscht.impl.typesafe.TypesafeRecipe.given
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class SeqNodeTest extends AnyFlatSpec with Matchers:
  "Seqence node parser" should "provide a singleton list of strings" in {
    cfg"key: value"[List[String]]("key") should contain theSameElementsInOrderAs List("value")
  }

  it should "provide a list of numbers" in {
    cfg"key: [42, 2.66]"[List[BigDecimal]]("key") should contain theSameElementsInOrderAs List(42, 2.66)
  }

  it should "provide a singleton list of numbers" in {
    cfg"key: ${Long.MaxValue}"[List[BigDecimal]]("key") should contain theSameElementsInOrderAs List(Long.MaxValue)
  }

  it should "provide a list of numbers from a string sequence" in {
    cfg"""key: ["42", "2.66"]"""[List[BigDecimal]]("key") should contain theSameElementsInOrderAs List(42, 2.66)
  }

  it should "provide a singleton list of numbers from a string value" in {
    cfg"""key: "${Long.MaxValue}""""[List[BigDecimal]]("key") should contain theSameElementsInOrderAs List(Long.MaxValue)
  }

  it should "provide a list of booleans" in {
    cfg"key: [true, true, false]"[List[Boolean]]("key") should contain theSameElementsInOrderAs List(true, true, false)
  }

  it should "provide a list of booleans from a string sequence" in {
    cfg"""key: ["false", "true", "false"]"""[List[Boolean]]("key") should contain theSameElementsInOrderAs
      List(false, true, false)
  }
