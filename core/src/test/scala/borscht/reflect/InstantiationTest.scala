package borscht.reflect

import borscht.CfgNode
import borscht.test.cfg
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class InstantiationTest extends AnyFlatSpec with Matchers:
  private val config = cfg("parameter" -> "value")

  "Parameterless instantiation" should "return a correct object with parameterless constructor" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithParameterlessConstructor], classOf[Superclass]))
    instance shouldBe a[ConfigurableWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithCfgConstructor], classOf[Superclass]))
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe None
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithBothConstructors], classOf[Superclass]))
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate(Subclass(classOf[ConfigurableWithoutAppropriateConstructor], classOf[Superclass]))
  }

  "None configuration instantiation" should "return a correct object with parameterless constructor" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithParameterlessConstructor], classOf[Superclass]), None)
    instance shouldBe a[ConfigurableWithParameterlessConstructor]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithCfgConstructor], classOf[Superclass]), None)
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe None
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithBothConstructors], classOf[Superclass]), None)
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe true
    instance.optCfg shouldBe None
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate(Subclass(classOf[ConfigurableWithoutAppropriateConstructor], classOf[Superclass]), None)
  }

  "Some configuration instantiation" should "fail without configuration constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate(Subclass(classOf[ConfigurableWithParameterlessConstructor], classOf[Superclass]), Some(config))
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithCfgConstructor], classOf[Superclass]), Some(config))
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithBothConstructors], classOf[Superclass]), Some(config))
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate(Subclass(classOf[ConfigurableWithoutAppropriateConstructor], classOf[Superclass]), Some(config))
  }

  "Configuration instantiation" should "fail without configuration constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate(Subclass(classOf[ConfigurableWithParameterlessConstructor], classOf[Superclass]), config)
  }

  it should "return a correct object with configuration constructor" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithCfgConstructor], classOf[Superclass]), config)
    instance shouldBe a[ConfigurableWithCfgConstructor]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "return a correct object with both constructors" in {
    val instance = instantiate(Subclass(classOf[ConfigurableWithBothConstructors], classOf[Superclass]), config)
    instance shouldBe a[ConfigurableWithBothConstructors]
    instance.isParameterless shouldBe false
    instance.optCfg shouldBe Some(config)
  }

  it should "fail without an appropriate constructor" in {
    a[NoSuchMethodException] should be thrownBy
      instantiate(Subclass(classOf[ConfigurableWithoutAppropriateConstructor], classOf[Superclass]), config)
  }

trait Superclass:
  def isParameterless: Boolean

  def optCfg: Option[CfgNode]

class ConfigurableWithParameterlessConstructor() extends Superclass:
  override def isParameterless: Boolean = true

  override def optCfg: Option[CfgNode] = None

class ConfigurableWithCfgConstructor(cfg: CfgNode) extends Superclass:
  override def isParameterless: Boolean = false

  override val optCfg: Option[CfgNode] = if (cfg == CfgNode.Empty) None else Some(cfg)

class ConfigurableWithBothConstructors(val cfg: CfgNode, val isParameterless: Boolean) extends Superclass:
  def this() = this(CfgNode.Empty, true)

  def this(cfg: CfgNode) = this(cfg, false)

  override val optCfg: Option[CfgNode] = if (cfg == CfgNode.Empty) None else Some(cfg)

class ConfigurableWithoutAppropriateConstructor(value: Int) extends Superclass:
  override def isParameterless: Boolean = false

  override def optCfg: Option[CfgNode] = None
