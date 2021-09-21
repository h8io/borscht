package borscht.typed.types

import borscht.{Meta, NodeParserException}
import borscht.test.{cfg, scalar, seq}
import borscht.typed.Ref
import borscht.typed.types.components.TestComponentWithBoxedParameter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ComponentRefTest extends AnyFlatSpec with Matchers:
  "Class component reference value type" should "create a correct component instance from scalar node" in {
    RefTypeComponent(scalar(classOf[RuntimeException].getName)).value match
      case e: RuntimeException => e.getMessage shouldBe null
      case _ => fail(s"Unexpected reference: ref")
  }

  it should "create a correct component instance without parameters" in {
    RefTypeComponent(cfg("class" -> classOf[RuntimeException].getName)).value match
      case e: RuntimeException => e.getMessage shouldBe null
      case _ => fail(s"Unexpected reference: ref")
  }

  it should "create a correct component instance with parameter" in {
    RefTypeComponent(cfg(
      "class" -> classOf[RuntimeException].getName,
      "parameters" -> seq("Exception component"))).value match
      case e: RuntimeException => e.getMessage shouldEqual "Exception component"
      case _ => fail(s"Unexpected reference: ref")
  }

  it should "create a correct component instance with two unnamed parameters" in {
    RefTypeComponent(cfg(
      "class" -> classOf[RuntimeException].getName,
      "parameters" -> seq(
        "Exception component",
        cfg("component" -> classOf[IllegalArgumentException].getName)))).value match
      case e: RuntimeException =>
        e.getMessage shouldEqual "Exception component"
        e.getCause shouldBe an[IllegalArgumentException]
      case _ => fail(s"Unexpected reference: ref")
  }

  it should "create a correct component instance with three named parameters" in {
    RefTypeComponent(cfg(
      "class" -> classOf[Tuple3[?, ?, ?]].getName,
      "parameters" -> cfg("_1" -> 42, "_2" -> true, "_3" -> "answer"))) shouldEqual Ref((42, true, "answer"))
  }

  it should "create a correct component with unnamed boxed parameter" in {
    RefTypeComponent(cfg(
      "class" -> classOf[TestComponentWithBoxedParameter].getName,
      "parameters" -> seq(42, Long.MinValue))) shouldEqual
      Ref(TestComponentWithBoxedParameter(42, Long.MinValue))
  }

  it should "create a correct component with named boxed parameter" in {
    RefTypeComponent(cfg(
      "class" -> classOf[TestComponentWithBoxedParameter].getName,
      "parameters" -> cfg("boxed" -> 42, "primitive" -> Long.MaxValue))) shouldEqual
      Ref(TestComponentWithBoxedParameter(42, Long.MaxValue))
  }

  "Object component reference value type" should "create a correct component instance from the cfg node" in {
    RefTypeComponent(cfg("object" -> "scala.None")).value shouldEqual None
  }

  it should "throw NodeParserException if the class do not have a companion object" in {
    a[NodeParserException] should be thrownBy RefTypeComponent(cfg("object" -> "java.util.Map"))
  }