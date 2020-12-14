package io.h8.cfg.impl.typesafe

import io.h8.cfg.parsers.given
import io.h8.cfg.Factory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

import scala.language.implicitConversions

class IterableNodeTest extends AnyFlatSpec with Matchers:
  import TypesafeFactory.given

  "Config" should "provide a list of strings" in {
    cfg"""key: [value1, value2]"""[List[String]]("key") must contain theSameElementsInOrderAs List("value1", "value2")
  }

  it should "provide a singleton list of strings" in {
    cfg"key: value"[List[String]]("key") must contain theSameElementsInOrderAs List("value")
  }

  it should "provide a list of numbers" in {
    cfg"key: [42, 2.66]"[List[Number]]("key") must contain theSameElementsInOrderAs List(42, 2.66)
  }

  it should "provide a singleton list of numbers" in {
    cfg"key: ${Long.MaxValue}"[List[Number]]("key") must contain theSameElementsInOrderAs List(Long.MaxValue)
  }

  it should "provide a list of numbers from a string sequence" in {
    cfg"""key: ["42", "2.66"]"""[List[Number]]("key") must contain theSameElementsInOrderAs List(42, 2.66)
  }

  it should "provide a singleton list of numbers from a string value" in {
    cfg"""key: "${Long.MaxValue}""""[List[Number]]("key") must contain theSameElementsInOrderAs List(Long.MaxValue)
  }

  it should "provide a list of booleans" in {
    cfg"key: [true, true, false]"[List[Boolean]]("key") must contain theSameElementsInOrderAs List(true, true, false)
  }

  it should "provide a list of booleans from a string sequence" in {
    cfg"""key: ["false", "true", "false"]"""[List[Boolean]]("key") must contain theSameElementsInOrderAs
      List(false, true, false)
  }
