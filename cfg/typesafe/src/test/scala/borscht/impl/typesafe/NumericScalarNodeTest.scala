package borscht.impl.typesafe

import borscht.Recipe
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class NumericScalarNodeTest extends AnyFlatSpec with Matchers:
  private val recipe = Recipe(provider = TypesafeConfigProvider())
  import recipe.given

  "Config" should "provide a byte value" in {
    val cfg = cfg"key: 42"
    cfg.get[Byte]("key") shouldEqual 42.toByte
  }

  it should "provide a byte value from a string" in {
    val cfg = cfg"""key: "42""""
    cfg.get[Byte]("key") shouldEqual 42.toByte
  }

  it should "provide a double value" in {
    val cfg = cfg"key: 2.66"
    cfg.get[Double]("key") shouldEqual 2.66
  }

  it should "provide a double value from a string" in {
    val cfg = cfg"""key: "2.66""""
    cfg.get[Double]("key") shouldEqual 2.66
  }

  it should "provide a float value" in {
    val cfg = cfg"key: 2.66"
    cfg.get[Float]("key") shouldEqual 2.66f
  }

  it should "provide a float value from a string" in {
    val cfg = cfg"""key: "2.66""""
    cfg.get[Float]("key") shouldEqual 2.66f
  }

  it should "provide an integer value" in {
    val cfg = cfg"key: ${Int.MaxValue}"
    cfg.get[Int]("key") shouldEqual Int.MaxValue
  }

  it should "provide an integer value from a string" in {
    val cfg = cfg"""key: "1""""
    cfg.get[Int]("key") shouldEqual 1
  }

  it should "provide a long value" in {
    val cfg = cfg"key: ${Long.MaxValue}"
    cfg.get[Long]("key") shouldEqual Long.MaxValue
  }

  it should "provide a long value from a string" in {
    val cfg = cfg"""key: "${Long.MinValue}""""
    cfg.get[Long]("key") shouldEqual Long.MinValue
  }

  it should "provide a short value" in {
    val cfg = cfg"key: 42"
    cfg.get[Short]("key") shouldEqual 42.toShort
  }

  it should "provide a short value from a string" in {
    val cfg = cfg"""key: "42""""
    cfg.get[Short]("key") shouldEqual 42.toShort
  }
