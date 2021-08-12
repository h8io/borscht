package borscht.parsers

import borscht.CfgNodeParserException
import borscht.reflect.*
import borscht.test.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ComponentRefNodeTest extends AnyFlatSpec with Matchers :
  private val configParameter = cfg("parameter" -> "value")
  private val parameters = cfg("cfg" -> configParameter)

  "Scalar component reference node" should "provide a correct object with parameterless constructor" in {
    val config = cfg("component" -> classOf[ComponentWithParameterlessConstructor].getName)
    val component = config[ComponentRef[Superclass]]("component").get
    component shouldBe a[ComponentWithParameterlessConstructor]
    component.isParameterless shouldBe true
    component.optCfg shouldBe None
  }

  it should "fail without parameterless constructor" in {
    val config = cfg("component" -> classOf[ComponentWithCfgConstructor].getName)
    a[CfgNodeParserException] should be thrownBy config[ComponentRef[Superclass]]("component")
  }

  it should "return a correct object with both constructors" in {
    val config = cfg("component" -> classOf[ComponentWithBothConstructors].getName)
    val component = config[ComponentRef[Superclass]]("component").get
    component shouldBe a[ComponentWithBothConstructors]
    component.isParameterless shouldBe true
    component.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    val config = cfg("component" -> classOf[ComponentWithoutAppropriateConstructor].getName)
    a[CfgNodeParserException] should be thrownBy config[ComponentRef[Superclass]]("component")
  }

  "Configuration component reference node without cfg" should
    "return a correct object with parameterless constructor" in {

    val config = cfg("class" -> classOf[ComponentWithParameterlessConstructor].getName)
    val component = config[ComponentRef[Superclass]]().get
    component shouldBe a[ComponentWithParameterlessConstructor]
    component.isParameterless shouldBe true
    component.optCfg shouldBe None
  }

  it should "fail without parameterless constructor" in {
    val config = cfg("class" -> classOf[ComponentWithCfgConstructor].getName)
    a[CfgNodeParserException] should be thrownBy config[ComponentRef[Superclass]]()
  }

  it should "return a correct object with both constructors" in {
    val config = cfg("class" -> classOf[ComponentWithBothConstructors].getName)
    val component = config[ComponentRef[Superclass]]().get
    component shouldBe a[ComponentWithBothConstructors]
    component.isParameterless shouldBe true
    component.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    val config = cfg("class" -> classOf[ComponentWithoutAppropriateConstructor].getName)
    a[CfgNodeParserException] should be thrownBy config[ComponentRef[Superclass]]()
  }

  "Configuration component reference node" should "fail without configuration constructor" in {
    val config = cfg("class" -> classOf[ComponentWithParameterlessConstructor].getName, "parameters" -> parameters)
    a[CfgNodeParserException] should be thrownBy config[ComponentRef[Superclass]]()
  }

  it should "return a correct object with configuration constructor" in {
    val config = cfg("class" -> classOf[ComponentWithCfgConstructor].getName, "parameters" -> parameters)
    val component = config[ComponentRef[Superclass]]().get
    component shouldBe a[ComponentWithCfgConstructor]
    component.isParameterless shouldBe false
    component.optCfg.map(_.map[String]()) shouldBe Some(configParameter.map[String]())
  }

  it should "return a correct object with both constructors" in {
    val config = cfg("class" -> classOf[ComponentWithBothConstructors].getName, "parameters" -> parameters)
    val component = config[ComponentRef[Superclass]]().get
    component shouldBe a[ComponentWithBothConstructors]
    component.isParameterless shouldBe false
    component.optCfg.map(_.map[String]()) shouldBe Some(configParameter.map[String]())
  }

  it should "fail without an appropriate constructor" in {
    val config = cfg("class" -> classOf[ComponentWithoutAppropriateConstructor].getName, "parameters" -> parameters)
    a[CfgNodeParserException] should be thrownBy config[ComponentRef[Superclass]]()
  }

  "Component" should "be created with multiple named parameters" in {
    val config = cfg("class" -> classOf[ComponentWithMultipleParameters].getName, "parameters" -> cfg(
      "cfg" -> configParameter,
      "str" -> "Answer",
      "value" -> 42,
      "is" -> true))
    config[ComponentRef[ComponentWithMultipleParameters]]().get shouldEqual
      ComponentWithMultipleParameters(configParameter, "Answer", 42, true)
  }

  it should "be created with multiple unnamed parameters" in {
    val config = cfg("class" -> classOf[ComponentWithMultipleParameters].getName, "parameters" ->
      seq(configParameter, "Answer", 42, true))
    config[ComponentRef[ComponentWithMultipleParameters]]().get shouldEqual
      ComponentWithMultipleParameters(configParameter, "Answer", 42, true)
  }

  it should "be created with a single parameter" in {
    val config = cfg("class" -> classOf[ComponentWithoutAppropriateConstructor].getName, "parameters" -> 42)
    config[ComponentRef[Superclass]]().get shouldEqual ComponentWithoutAppropriateConstructor(42)
  }