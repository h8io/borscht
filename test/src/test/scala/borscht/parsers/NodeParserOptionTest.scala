package borscht.parsers

import borscht.NodeParserException
import borscht.test.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserOptionTest extends AnyFlatSpec with Matchers:
  "Option node parser" should "return None object" in {
    seq().as[Option[String]] shouldBe None
  }

  it should "return Some instance for a scalar node" in {
    scalar("h8.io").as[Option[String]] shouldEqual Some("h8.io")
  }

  it should "return Some instance for a sequence node with a single element" in {
    seq(42).as[Option[Int]] shouldEqual Some(42)
  }

  it should "throw an exception for a sequence node with multiple elements" in {
    an[NodeParserException] should be thrownBy seq(1L, 2L, 3L).as[Option[Long]]
  }

  it should "retrieve a list from the nested sequesnce nodes" in {
    seq(seq(1, 2, 3)).as[Option[List[Int]]] shouldEqual Some(List(1, 2, 3))
  }