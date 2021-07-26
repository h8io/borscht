package borscht.parsers

import borscht.reflect._
import borscht.test.cfg
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ComponentRefNodeTest extends AnyFlatSpec with Matchers :
  private val parameter = cfg("parameter" -> "value")

  "Scalar component reference node" should "provide a correct object with parameterless constructor" in {
    val config = cfg("component" -> "borscht.reflect.ConfigurableWithParameterlessConstructor")
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "return a correct object with configuration constructor" in {
    val config = cfg("component" -> "borscht.reflect.ConfigurableWithCfgConstructor")
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe None
  }

  it should "return a correct object with both constructors" in {
    val config = cfg("component" -> "borscht.reflect.ConfigurableWithBothConstructors")
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    val config = cfg("component" -> "borscht.reflect.ConfigurableWithoutAppropriateConstructor")
    val component = config[ComponentRef[Superclass]]("component")
    a[NoSuchMethodException] should be thrownBy component.instance
  }

  "Configuration component reference node without cfg" should
    "return a correct object with parameterless constructor" in {

    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithParameterlessConstructor"))
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "return a correct object with configuration constructor" in {
    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithCfgConstructor"))
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe None
  }

  it should "return a correct object with both constructors" in {
    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithBothConstructors"))
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithoutAppropriateConstructor"))
    val component = config[ComponentRef[Superclass]]("component")
    a[NoSuchMethodException] should be thrownBy component.instance
  }

  "Configuration component reference node" should "fail without configuration constructor" in {
    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithParameterlessConstructor", "cfg" -> parameter))
    val component = config[ComponentRef[Superclass]]("component")
    a[NoSuchMethodException] should be thrownBy component.instance
  }

  it should "return a correct object with configuration constructor" in {
    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithCfgConstructor", "cfg" -> parameter))
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg.map(_.map[String]()) shouldBe Some(parameter.map[String]())
  }

  it should "return a correct object with both constructors" in {
    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithBothConstructors", "cfg" -> parameter))
    val instance = config[ComponentRef[Superclass]]("component").instance
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg.map(_.map[String]()) shouldBe Some(parameter.map[String]())
  }

  it should "fail without an appropriate constructor" in {
    val config = cfg("component" ->
      cfg("class" -> "borscht.reflect.ConfigurableWithoutAppropriateConstructor", "cfg" -> parameter))
    val component = config[ComponentRef[Superclass]]("component")
    a[NoSuchMethodException] should be thrownBy component.instance
  }
