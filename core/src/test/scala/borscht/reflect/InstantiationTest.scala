package borscht.reflect

import borscht.CfgNode
import borscht.test.cfg
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InstantiationTest extends AnyFlatSpec with Matchers:
  private val config = cfg("parameter" -> "value")

  "Parameterless instantiation" should "return a correct object with parameterless constructor" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithParameterlessConstructor])
    instance shouldBe a[ConfigurableWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithCfgConstructor])
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe None
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithBothConstructors])
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate[Superclass](classOf[ConfigurableWithoutAppropriateConstructor])
  }

  "None configuration instantiation" should "return a correct object with parameterless constructor" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithParameterlessConstructor], None)
    instance shouldBe a[ConfigurableWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithCfgConstructor], None)
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe None
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithBothConstructors], None)
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate[Superclass](classOf[ConfigurableWithoutAppropriateConstructor], None)
  }

  "Some configuration instantiation" should "fail without configuration constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate[Superclass](classOf[ConfigurableWithParameterlessConstructor], Some(config))
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithCfgConstructor], Some(config))
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithBothConstructors], Some(config))
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate[Superclass](classOf[ConfigurableWithoutAppropriateConstructor], Some(config))
  }

  "Configuration instantiation" should "fail without configuration constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate[Superclass](classOf[ConfigurableWithParameterlessConstructor], config)
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithCfgConstructor], config)
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate[Superclass](classOf[ConfigurableWithBothConstructors], config)
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate[Superclass](classOf[ConfigurableWithoutAppropriateConstructor], config)
  }
