package borscht.typed.types

import borscht.test.scalar
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.util.Right

class ValueTypeCharTest extends AnyFlatSpec with Matchers:
  "Char value type" should "parse characters correctly" in {
    ValueTypeChar().parser(Nil) map (_(scalar("*"))) shouldEqual Right('*')
    ValueTypeChar().parser(Nil) map (_(scalar(48))) shouldEqual Right('0')
    ValueTypeChar().parser(Nil) map (_(scalar("\\u0020"))) shouldEqual Right(' ')
  }