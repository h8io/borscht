package borscht.parsers

import borscht.CfgNodeParserException
import borscht.test._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class NodeParserClassTest extends AnyFlatSpec with Matchers :
  "Class node parser" should "return the correct class object" in {
    cfg("key" -> "java.lang.Integer")[Class[_]]("key") shouldEqual classOf[Integer]
  }
