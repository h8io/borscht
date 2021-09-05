package borscht.typed

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RefTest extends AnyFlatSpec with Matchers:
  "cast" should "properly cast object references" in {
    val ref: Ref[Any] = Ref[Integer](classOf[Integer], throw RuntimeException()).cast[Any]
    ref.`class` shouldEqual classOf[Integer]
  }

  it should "properly cast value references to themselves" in {
    val ref: Ref[Int] = Ref[Any](classOf[Int], 1).cast[Int]
    ref.`class` shouldEqual classOf[Int]
    ref.value shouldEqual 1
    ref.value.getClass shouldBe classOf[Int]
  }

  it should "properly cast primitive value references to the assotiated boxed type" in {
    val ref: Ref[Integer] = Ref[Any](classOf[Int], 1).cast[Integer]
    ref.`class` shouldEqual classOf[Integer]
    ref.value shouldEqual 1
    ref.value.getClass shouldBe classOf[Integer]
  }

  it should "properly cast primitive value references to Any" in {
    val ref: Ref[Any] = Ref[Int](classOf[Int], 1).cast[Any]
    ref.`class` shouldEqual classOf[Integer]
    ref.value shouldEqual 1
    ref.value.getClass shouldBe classOf[Integer]
  }

  it should "properly cast boxed value references to the assotiated primitive type" in {
    val ref: Ref[Int] = Ref[Any](classOf[Integer], Integer.valueOf(1)).cast[Int]
    ref.`class` shouldEqual classOf[Int]
    ref.value shouldEqual 1
    ref.value.getClass shouldBe classOf[Int]
  }

  it should "throw an exception for incompatible classes" in {
    a[ClassCastException] should be thrownBy Ref[Integer](classOf[Integer], throw RuntimeException()).cast[String]
  }
