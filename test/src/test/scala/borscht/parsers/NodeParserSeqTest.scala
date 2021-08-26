package borscht.parsers

import borscht.test.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserSeqTest extends AnyFlatSpec with Matchers:
  "Sequence node parser" should "provide a singleton list of strings" in {
    cfg("key" -> "value")[List[String]]("key") should contain theSameElementsInOrderAs List("value")
  }

  it should "provide a list of numbers" in {
    seq(42, 2.66).as[List[BigDecimal]] should contain theSameElementsInOrderAs List(42, 2.66)
  }

  it should "provide a singleton list of numbers" in {
    cfg("key" -> Long.MaxValue)[List[BigDecimal]]("key") should contain theSameElementsInOrderAs
      List(Long.MaxValue)
  }

  it should "provide a list of numbers from a string sequence" in {
    seq("42", "2.66").as[List[BigDecimal]] should contain theSameElementsInOrderAs List(42, 2.66)
  }

  it should "provide a singleton list of numbers from a string value" in {
    cfg("key" -> Long.MaxValue)[List[BigDecimal]]("key") should contain theSameElementsInOrderAs
      List(Long.MaxValue)
  }

  it should "provide a list of booleans" in {
    seq(true, true, false).as[List[Boolean]] should contain theSameElementsInOrderAs List(true, true, false)
  }

  it should "provide a list of booleans from a string sequence" in {
    seq("false", "true", "false").as[List[Boolean]] should contain theSameElementsInOrderAs
      List(false, true, false)
  }
