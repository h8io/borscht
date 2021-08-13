package borscht.template

import borscht.*

class TestTemplate(template: String) extends Template:
  override def render: String = ???

  override def set(key: String, value: Any): TestTemplate = ???
