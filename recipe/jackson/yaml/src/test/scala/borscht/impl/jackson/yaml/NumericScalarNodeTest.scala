package borscht.impl.jackson.yaml

import borscht.Recipe
import borscht.impl.jackson.yaml.YamlRecipe.given
import borscht.parsers.given
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class NumericScalarNodeTest extends AnyFlatSpec with Matchers:

  it should "provide a byte value from a string" in {
    cfg"""key: "42""""[Byte]("key") mustEqual 42.toByte
  }

  it should "provide a double value" in {
    cfg"key: 2.66"[Double]("key") mustEqual 2.66
  }

  it should "provide a double value from a string" in {
    cfg"""key: "2.66""""[Double]("key") mustEqual 2.66
  }

  it should "provide a float value" in {
    cfg"key: 2.66"[Float]("key") mustEqual 2.66f
  }

  it should "provide a float value from a string" in {
    cfg"""key: "2.66""""[Float]("key") mustEqual 2.66f
  }

  it should "provide an integer value" in {
    cfg"key: ${Int.MaxValue}"[Int]("key") mustEqual Int.MaxValue
  }

  it should "provide an integer value from a string" in {
    cfg"""key: "1""""[Int]("key") mustEqual 1
  }

  it should "provide a long value" in {
    cfg"key: ${Long.MaxValue}"[Long]("key") mustEqual Long.MaxValue
  }

  it should "provide a long value from a string" in {
    cfg"""key: "${Long.MinValue}""""[Long]("key") mustEqual Long.MinValue
  }

  it should "provide a short value" in {
    cfg"key: 42"[Short]("key") mustEqual 42.toShort
  }

  it should "provide a short value from a string" in {
    cfg"""key: "42""""[Short]("key") mustEqual 42.toShort
  }
