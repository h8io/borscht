package borscht.reflect

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CastTest extends AnyFlatSpec with Matchers:
  "Function `cast`" should "cast the class to the correct superclass" in {
    val result: Class[? <: Number] = cast[Number](Class.forName("java.lang.Double"))
    result shouldEqual classOf[java.lang.Double]
  }

  it should "fail if classes are incompatible" in {
    an[ClassCastException] should be thrownBy cast[String](classOf[Integer])
  }
