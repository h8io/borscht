package borscht.typed.types

import borscht.test.scalar
import borscht.typed.Ref
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CharTypeTest extends AnyFlatSpec with Matchers:
  "Char value type" should "parse characters correctly" in {
    RefTypeChar.parser(Nil) map (_(scalar("*"))) shouldEqual Right(Ref('*'))
    RefTypeChar.parser(Nil) map (_(scalar(48))) shouldEqual Right(Ref('0'))
    RefTypeChar.parser(Nil) map (_(scalar("\\u0020"))) shouldEqual Right(Ref(' '))
  }