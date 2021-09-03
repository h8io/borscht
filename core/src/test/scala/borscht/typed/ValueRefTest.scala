package borscht.typed

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ValueRefTest extends AnyFlatSpec with Matchers:
  "cast" should "properly cast object references" in {
    val ref: ValueRef[Any] = ValueRef[Integer](classOf[Integer], throw RuntimeException()).cast[Any]
    ref.`class` shouldEqual classOf[Integer]
  }

  it should "properly cast value references to themselves" in {
    val ref: ValueRef[Int] = ValueRef[Any](classOf[Int], 1).cast[Int]
    ref.`class` shouldEqual classOf[Int]
    ref.value shouldEqual 1
    ref.value.getClass shouldBe classOf[Int]
  }

  it should "throw an exception for incompatible classes" in {
    a[ClassCastException] should be thrownBy ValueRef[Integer](classOf[Integer], throw RuntimeException()).cast[String]
  }
