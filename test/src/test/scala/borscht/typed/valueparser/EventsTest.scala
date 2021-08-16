package borscht.typed.valueparser

import borscht.typed.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.implicitConversions

class EventsTest extends AnyFlatSpec with Matchers :
  import Event.*

  "Events" should "parse an empty string" in {
    Events("".toIndexedSeq).toList should contain theSameElementsAs List(End(0))
  }

  it should "parse whitespace string" in {
    Events(" ".repeat(5).toIndexedSeq).toList should contain theSameElementsAs List(End(5))
  }

  it should "ignore whitespaces" in {
    Events(" abc def [  ] ,".toIndexedSeq).toList should contain theSameElementsInOrderAs
      List(TypeName("abc", 1), TypeName("def", 5), TypeListStart(9), TypeListEnd(12), TypeListSeparator(14), End(15))
  }

  it should "parse string without whitespaces" in {
    Events("abc[def[ghi,jkl],mno[],pqr]".toIndexedSeq).toList should contain theSameElementsInOrderAs
      List(
        TypeName("abc", 0), TypeListStart(3),
        TypeName("def", 4), TypeListStart(7),
        TypeName("ghi", 8), TypeListSeparator(11),
        TypeName("jkl", 12),
        TypeListEnd(15), TypeListSeparator(16),
        TypeName("mno", 17), TypeListStart(20), TypeListEnd(21), TypeListSeparator(22),
        TypeName("pqr", 23), TypeListEnd(26),
        End(27))
  }
