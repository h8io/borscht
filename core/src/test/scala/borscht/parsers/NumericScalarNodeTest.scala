package borscht.parsers

import borscht.test._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NumericScalarNodeTest extends AnyFlatSpec with Matchers:
  "Numeric node parser" should "provide a byte value from a string" in {
    cfg("key" -> "42")[Byte]("key") shouldEqual 42.toByte
  }

  it should "provide a double value" in {
    cfg("key" -> 2.66)[Double]("key") shouldEqual 2.66
  }

  it should "provide a double value from a string" in {
    cfg("key" -> "2.66")[Double]("key") shouldEqual 2.66
  }

  it should "provide a float value" in {
    cfg("key" -> 2.66)[Float]("key") shouldEqual 2.66f
  }

  it should "provide a float value from a string" in {
    cfg("key" -> "2.66")[Float]("key") shouldEqual 2.66f
  }

  it should "provide an integer value" in {
    cfg("key" -> Int.MaxValue)[Int]("key") shouldEqual Int.MaxValue
  }

  it should "provide an integer value from a string" in {
    cfg("key" -> "1")[Int]("key") shouldEqual 1
  }

  it should "provide a long value" in {
    cfg("key" -> Long.MaxValue)[Long]("key") shouldEqual Long.MaxValue
  }

  it should "provide a long value from a string" in {
    cfg("key" -> s"${Long.MinValue}")[Long]("key") shouldEqual Long.MinValue
  }

  it should "provide a short value" in {
    cfg("key" -> 42)[Short]("key") shouldEqual 42.toShort
  }

  it should "provide a short value from a string" in {
    cfg("key" -> "42")[Short]("key") shouldEqual 42.toShort
  }
