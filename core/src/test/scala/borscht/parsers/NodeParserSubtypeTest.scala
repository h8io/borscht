package borscht.parsers

import borscht.CfgNodeParserException
import borscht.reflect.Subclass
import borscht.test._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserSubtypeTest extends AnyFlatSpec with Matchers :
  // It works because of type erasure
  "Subtype node parser" should "return the correct subclass object" in {
    cfg("key" -> "java.lang.Integer")[Subclass[Number]]("key") shouldEqual
      Subclass(classOf[Integer], classOf[Number])
  }

  it should "fail if classes are incompatible" in {
    an[CfgNodeParserException] should be thrownBy cfg("key" -> "java.lang.Integer")[Subclass[String]]("key")
  }
