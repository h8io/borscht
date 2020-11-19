package borscht.impl.typesafe

import borscht.Engine
import borscht.parsers.{given _}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class IterableNodeTest extends AnyFlatSpec with Matchers:
  private val engine = Engine(provider = TypesafeConfigProvider())
  import engine.{given _}

  "Config" should "provide a list of strings" in {
    cfg"key: [value1, value2]".get[List[String]]("key").toSeq should contain theSameElementsInOrderAs
      List("value1", "value2")
  }

  it should "provide a singleton list of strings" in {
    cfg"key: value".get[List[String]]("key").toSeq should contain theSameElementsInOrderAs
      List("value")
  }

  it should "provide a list of numbers" in {
    cfg"key: [42, 2.66]".get[List[Number]]("key").toSeq should contain theSameElementsInOrderAs
      List(42, 2.66)
  }

  it should "provide a singleton list of numbers" in {
    cfg"key: ${Long.MaxValue}".get[List[Number]]("key").toSeq should contain theSameElementsInOrderAs
      List(Long.MaxValue)
  }

  it should "provide a list of numbers from a string sequence" in {
    cfg"""key: ["42", "2.66"]""".get[List[Number]]("key").toSeq should contain theSameElementsInOrderAs
      List(42, 2.66)
  }

  it should "provide a singleton list of numbers from a string value" in {
    cfg"""key: "${Long.MaxValue}"""".get[List[Number]]("key").toSeq should contain theSameElementsInOrderAs
      List(Long.MaxValue)
  }

  it should "provide a list of booleans" in {
    cfg"key: [true, true, false]".get[List[Boolean]]("key").toSeq should contain theSameElementsInOrderAs
      List(true, true, false)
  }

  it should "provide a list of booleans from a string sequence" in {
    cfg"""key: ["false", "true", "false"]""".get[List[Boolean]]("key").toSeq should contain theSameElementsInOrderAs
      List(false, true, false)
  }
