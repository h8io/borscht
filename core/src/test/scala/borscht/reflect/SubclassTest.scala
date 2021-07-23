package borscht.reflect

import borscht.CfgNodeParserException
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SubclassTest extends AnyFlatSpec with Matchers:
  "Subclass constructor" should "create the correct subclass object from the class object" in {
    Subclass[Number](classOf[java.lang.Double]) shouldEqual Subclass(classOf[java.lang.Double], classOf[Number])
  }

  it should "create the correct subclass object from the class name" in {
    Subclass[Number]("java.lang.Integer") shouldEqual Subclass(classOf[Integer], classOf[Number])
  }

  it should "fail if classes are incompatible" in {
    an[ClassCastException] should be thrownBy Subclass[String]("java.lang.Integer")
  }
