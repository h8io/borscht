package borscht.parsers

import borscht.test.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserListTest extends AnyFlatSpec with Matchers:
  "List node parser" should "provide a singleton list of strings" in {
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

  it should "provide a list of lists" in {
    seq(
      seq(1, 2, 3),
      seq(4, 5, 6),
      seq(7, 8, 9)).as[List[List[Int]]] should contain theSameElementsInOrderAs
      List(List(1, 2, 3), List(4, 5, 6), List(7, 8, 9))
  }

  it should "provide a list of options" in {
    seq(seq(), seq(42), 7).as[List[Option[Int]]] should contain theSameElementsInOrderAs
      List(None, Some(42), Some(7))
  }
