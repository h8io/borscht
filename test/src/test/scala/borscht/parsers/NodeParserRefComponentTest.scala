package borscht.parsers

import borscht.test.*
import borscht.typed.RefComponent
import borscht.typed.types.components.TestComponentWithBoxedParameter
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import java.util.concurrent.atomic.{AtomicInteger, AtomicStampedReference}
import scala.reflect.ClassTag

class NodeParserRefComponentTest extends AnyFlatSpec with Matchers:
  "Component reference scalar node" should "provide the correct component" in {
    val component = scalar(classOf[java.util.HashMap[?, ?]].getName).as[RefComponent[java.util.Map[?, ?]]]
    component.classTag shouldEqual ClassTag(classOf[java.util.HashMap[?, ?]])
    component.value shouldBe a[java.util.HashMap[?, ?]]
    component.value shouldBe empty
  }

  "Component reference cfg node" should "provide the correct component without parameters" in {
    val component =
      cfg("class" -> classOf[java.util.HashMap[?, ?]].getName).as[RefComponent[java.util.Map[?, ?]]]
    component.classTag shouldEqual ClassTag(classOf[java.util.HashMap[?, ?]])
    component.value shouldBe a[java.util.HashMap[?, ?]]
    component.value shouldBe empty
  }

  it should "provide the correct component with unnamed parameter" in {
    val component = cfg(
      "class" -> classOf[AtomicInteger].getName,
      "parameters" -> "int:42"
    ).as[RefComponent[AtomicInteger]]
    component.classTag shouldEqual ClassTag(classOf[AtomicInteger])
    component.value shouldBe a[AtomicInteger]
    component.value.get shouldEqual 42
  }

  it should "provide the correct component with unnamed parameters" in {
    val component = cfg(
      "class" -> classOf[AtomicStampedReference[?]].getName,
      "parameters" -> seq("something", 42)
    ).as[RefComponent[AtomicStampedReference[?]]]
    component.classTag shouldEqual ClassTag(classOf[AtomicStampedReference[?]])
    component.value shouldBe a[AtomicStampedReference[?]]
    component.value.getReference shouldEqual "something"
    component.value.getStamp shouldEqual 42
  }

  it should "provide the correct component with named parameters" in {
    val component = cfg(
      "class" -> classOf[TestComponentWithBoxedParameter].getName,
      "parameters" -> cfg("boxed" -> s"int:${Int.MaxValue}", "primitive" -> "long:42")
    ).as[RefComponent[TestComponentWithBoxedParameter]]
    component.classTag shouldEqual ClassTag(classOf[TestComponentWithBoxedParameter])
    component.value shouldBe a[TestComponentWithBoxedParameter]
    component.value.boxed shouldEqual Int.MaxValue
    component.value.primitive shouldEqual 42L
  }
