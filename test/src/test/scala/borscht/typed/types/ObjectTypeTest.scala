package borscht.typed.types

import borscht.NodeParserException
import borscht.test.scalar
import borscht.typed.RefObj
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ObjectTypeTest extends AnyFlatSpec with Matchers:
  "Object reference type" should "return the correct Scala object" in {
    RefTypeObject(scalar("scala.None")) shouldEqual RefObj(None)
  }

  it should "throw NodeParserException if the class do not have a companion object" in {
    a[NodeParserException] should be thrownBy RefTypeObject(scalar("java.util.Map"))
  }
