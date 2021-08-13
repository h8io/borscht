package borscht.template

import borscht.*

import scala.collection.mutable

class TestTemplate(val engine: String, val template: String) extends Template:
  private val _parameters = mutable.SortedMap[String, Any]()

  override def render: String = s"$engine -> $template: ${_parameters.mkString("{", ", ", "}")}"

  override def set(key: String, value: Any): TestTemplate =
    _parameters += key -> value
    this

  def parameters: Iterator[(String, Any)] = _parameters.iterator
