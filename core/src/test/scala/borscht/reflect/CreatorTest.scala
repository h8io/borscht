package borscht.reflect

import borscht.CfgNode
import borscht.test.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class CreatorTest extends AnyFlatSpec with Matchers:
  private val configParameter = cfg("parameter" -> "value")
  private val config = cfg("cfg" -> configParameter)

  "Parameterless creator" should "return a correct object with parameterless constructor" in {
    val instance = creator[Superclass](classOf[ComponentWithParameterlessConstructor])()
    instance shouldBe a[ComponentWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without parameterless constructor" in {
    a[NoSuchMethodException] should be thrownBy creator[Superclass](classOf[ComponentWithCfgConstructor])
  }

  it should "return a correct object with both constructors" in {
    val instance = creator[Superclass](classOf[ComponentWithBothConstructors])()
    instance shouldBe a[ComponentWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      creator[Superclass](classOf[ComponentWithoutAppropriateConstructor])
  }

  "None configuration instantiation" should "return a correct object with parameterless constructor" in {
    val instance = creator[Superclass](classOf[ComponentWithParameterlessConstructor], None)()
    instance shouldBe a[ComponentWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without parameterless constructor" in {
    a[NoSuchMethodException] should be thrownBy creator[Superclass](classOf[ComponentWithCfgConstructor], None)
  }

  it should "return a correct object with both constructors" in {
    val instance = creator[Superclass](classOf[ComponentWithBothConstructors], None)()
    instance shouldBe a[ComponentWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      creator[Superclass](classOf[ComponentWithoutAppropriateConstructor], None)
  }

  "Some configuration instantiation" should "fail without configuration constructor" in {
    a[NoSuchMethodException] should be thrownBy
      creator[Superclass](classOf[ComponentWithParameterlessConstructor], Some(config))
  }

  it should "return a correct object with configuration constructor" in {
    val instance = creator[Superclass](classOf[ComponentWithCfgConstructor], Some(config))()
    instance shouldBe a[ComponentWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(configParameter)
  }

  it should "return a correct object with both constructors" in {
    val instance = creator[Superclass](classOf[ComponentWithBothConstructors], Some(config))()
    instance shouldBe a[ComponentWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(configParameter)
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      creator[Superclass](classOf[ComponentWithoutAppropriateConstructor], Some(config))
  }

  "Configuration instantiation" should "fail without configuration constructor" in {
    a[NoSuchMethodException] should be thrownBy
      creator[Superclass](classOf[ComponentWithParameterlessConstructor], config)
  }

  it should "return a correct object with configuration constructor" in {
    val instance = creator[Superclass](classOf[ComponentWithCfgConstructor], config)()
    instance shouldBe a[ComponentWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(configParameter)
  }

  it should "return a correct object with both constructors" in {
    val instance = creator[Superclass](classOf[ComponentWithBothConstructors], config)()
    instance shouldBe a[ComponentWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(configParameter)
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      creator[Superclass](classOf[ComponentWithoutAppropriateConstructor], config)
  }

  "Object" should "be created with unnamed parameters singleton list" in {
    val instance =
      creator[Superclass](classOf[ComponentWithBothConstructors], seq(configParameter))()
    instance shouldBe a[ComponentWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(configParameter)
  }

  it should "be created with unnamed parameter" in {
    creator[Superclass](classOf[ComponentWithoutAppropriateConstructor], Some(scalar(1)))() shouldEqual
      ComponentWithoutAppropriateConstructor(1)
  }

  it should "be created with named parameters" in {
    creator(
      classOf[ComponentWithMultipleParameters],
      cfg(
        "cfg" -> configParameter,
        "str" -> "Answer",
        "value" -> 42,
        "is" -> true))() shouldEqual ComponentWithMultipleParameters(configParameter, "Answer", 42, true)
  }

  it should "be created with unnamed parameters" in {
    creator(classOf[ComponentWithMultipleParameters], seq(configParameter, "Answer", 42, true))() shouldEqual
      ComponentWithMultipleParameters(configParameter, "Answer", 42, true)
  }
