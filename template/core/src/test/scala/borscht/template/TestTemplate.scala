package borscht.template

import borscht.*

import scala.collection.mutable

class TestTemplate(val engine: String, val template: String) extends Template:
  private val parameters = mutable.SortedMap[String, Any]()

  override def render: String = s"$engine -> $template: ${parameters.mkString("{", ", ", "}")}"

  override def set(key: String, value: Any): TestTemplate =
    parameters += key -> value
    this
