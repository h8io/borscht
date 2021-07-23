package borscht.parsers

import borscht.CfgNodeParserException
import borscht.test._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class JavaTypeNodeTest extends AnyFlatSpec with Matchers :
  // It works because of type erasure
  "Class node accessor" should "return the correct class object" in {
    cfg("key" -> "java.lang.Integer")[Class[Number]]("key") shouldEqual classOf[Integer]
  }

  it should "fail if classes are incompatible" in {
    an[CfgNodeParserException] should be thrownBy cfg("key" -> "java.lang.Integer")[Class[String]]("key")
  }
